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
package org.openmuc.jasn1.ber;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class BerLength {

    public int val;

    public BerLength() {
    }

    public int decode(InputStream is) throws IOException {

        val = is.read();
        if (val == -1) {
            throw new EOFException("Unexpected end of input stream.");
        }

        if ((val & 0x80) == 0) {
            return 1;
        }

        int lengthLength = val & 0x7f;

        // check for indefinite length
        if (lengthLength == 0) {
            val = -1;
            return 1;
        }

        if (lengthLength > 4) {
            throw new IOException("Length is out of bound!");
        }

        val = 0;

        for (int i = 0; i < lengthLength; i++) {
            int nextByte = is.read();
            if (nextByte == -1) {
                throw new EOFException("Unexpected end of input stream.");
            }
            val |= nextByte << (8 * (lengthLength - i - 1));
        }

        return lengthLength + 1;
    }

    public static int skip(InputStream is) throws IOException {

        int val = is.read();
        if (val == -1) {
            throw new EOFException("Unexpected end of input stream.");
        }

        if ((val & 0x80) == 0) {
            return 1;
        }

        int lengthLength = val & 0x7f;

        // check for indefinite length
        if (lengthLength == 0) {
            val = -1;
            return 1;
        }

        if (lengthLength > 4) {
            throw new IOException("Length is out of bound!");
        }

        for (int i = 0; i < lengthLength; i++) {
            int nextByte = is.read();
            if (nextByte == -1) {
                throw new EOFException("Unexpected end of input stream.");
            }
        }

        return lengthLength + 1;
    }

    private static int MIN_LARGE_LEN = 128;
    private static final int LEN_BITS_PER_OCTET = 8;

    public static int encodeLength(BerByteArrayOutputStream os, int length) throws IOException {
        if (length < 0) {
            throw new IllegalArgumentException("Invalid negative length: " + length);
        }
        if (length < MIN_LARGE_LEN) {
            os.write(length);
            return 1;
        }
        final int lenBits = Integer.SIZE - Integer.numberOfLeadingZeros(length);
        final int lenOctets = lenBits / LEN_BITS_PER_OCTET + (lenBits % LEN_BITS_PER_OCTET > 0 ? 1 : 0);
        for (int i = 0; i < lenOctets; i++) {
            os.write((length >>> (LEN_BITS_PER_OCTET * i)) & 0xFF);
        }
        os.write(0x80 | lenOctets);
        return 1 + lenOctets;
    }
}
