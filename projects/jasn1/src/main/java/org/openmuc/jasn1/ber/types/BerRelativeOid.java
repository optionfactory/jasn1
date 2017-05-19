/*
 * Copyright 2011-17 Fraunhofer ISE
 *
 * This file is part of jASN1.
 * For more information visit http://www.openmuc.org
 *
 * jASN1 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jASN1 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jASN1.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openmuc.jasn1.ber.types;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import org.openmuc.jasn1.ber.BerLength;
import org.openmuc.jasn1.ber.BerTag;

public class BerRelativeOid {

    public final static BerTag tag = new BerTag(BerTag.UNIVERSAL_CLASS, BerTag.PRIMITIVE, BerTag.RELATIVE_OID_TAG);

    public byte[] code = null;

    public int[] value;

    public BerRelativeOid() {
    }

    public BerRelativeOid(byte[] code) {
        this.code = code;
    }

    public BerRelativeOid(int[] value) {
        for (int objectIdentifierComponent : value) {
            if (objectIdentifierComponent < 0) {
                throw new IllegalArgumentException("invalid object identifier components");
            }
        }
        this.value = value;

    }

    public int encode(BerByteArrayOutputStream os) throws IOException {
        return encode(os, true);
    }

    public int encode(BerByteArrayOutputStream os, boolean withTag) throws IOException {

        if (code != null) {
            for (int i = code.length - 1; i >= 0; i--) {
                os.write(code[i]);
            }
            if (withTag) {
                return tag.encode(os) + code.length;
            }
            return code.length;
        }

        int subidentifier;
        int codeLength = 0;

        for (int i = (value.length - 1); i >= 0; i--) {
            subidentifier = value[i];

            // get length of subidentifier
            int subIDLength = 1;
            while (subidentifier > (Math.pow(2, (7 * subIDLength)) - 1)) {
                subIDLength++;
            }

            os.write(subidentifier & 0x7f);

            for (int j = 1; j <= (subIDLength - 1); j++) {
                os.write(((subidentifier >> (7 * j)) & 0xff) | 0x80);
            }

            codeLength += subIDLength;
        }

        codeLength += BerLength.encodeLength(os, codeLength);

        if (withTag) {
            codeLength += tag.encode(os);
        }

        return codeLength;
    }

    public int decode(InputStream is) throws IOException {
        return decode(is, true);
    }

    public int decode(InputStream is, boolean withTag) throws IOException {

        int codeLength = 0;

        if (withTag) {
            codeLength += tag.decodeAndCheck(is);
        }

        BerLength length = new BerLength();
        codeLength += length.decode(is);

        if (length.val == 0) {
            value = new int[0];
            return codeLength;
        }

        byte[] byteCode = new byte[length.val];
        Util.readFully(is, byteCode);

        codeLength += length.val;

        List<Integer> objectIdentifierComponentsList = new ArrayList<>();

        int subIDEndIndex = 0;

        while (subIDEndIndex < length.val) {
            int subIDStartIndex = subIDEndIndex;

            while ((byteCode[subIDEndIndex] & 0x80) == 0x80) {
                if (subIDEndIndex == (length.val - 1)) {
                    throw new IOException("Invalid Object Identifier");
                }
                subIDEndIndex++;
            }
            int subidentifier = 0;
            for (int j = subIDStartIndex; j <= subIDEndIndex; j++) {
                subidentifier |= ((byteCode[j] & 0x7f) << ((subIDEndIndex - j) * 7));
            }
            objectIdentifierComponentsList.add(subidentifier);
            subIDEndIndex++;
        }

        value = new int[objectIdentifierComponentsList.size()];
        for (int i = 0; i < objectIdentifierComponentsList.size(); i++) {
            value[i] = objectIdentifierComponentsList.get(i);
        }

        return codeLength;

    }

    @Override
    public String toString() {
        if (value == null || value.length == 0) {
            return "";
        }

        String objIDString = "";
        objIDString += value[0];
        for (int i = 1; i < value.length; i++) {
            objIDString += "." + value[i];
        }
        return objIDString;
    }

}
