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
package org.openmuc.jasn1.compiler;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.junit.Assert;
import org.junit.Test;
import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import org.openmuc.jasn1.ber.types.BerNull;
import org.openmuc.jasn1.ber.types.BerObjectIdentifier;
import org.openmuc.jasn1.ber.types.BerOctetString;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;
import org.openmuc.jasn1.compiler.pedefinitions.Fcp;
import org.openmuc.jasn1.compiler.pedefinitions.FileManagement;
import org.openmuc.jasn1.compiler.pedefinitions.PEGenericFileManagement;
import org.openmuc.jasn1.compiler.pedefinitions.PEHeader;
import org.openmuc.jasn1.compiler.pedefinitions.PEPUKCodes;
import org.openmuc.jasn1.compiler.pedefinitions.PUKConfiguration;
import org.openmuc.jasn1.compiler.pedefinitions.PUKKeyReferenceValue;
import org.openmuc.jasn1.compiler.pedefinitions.ProfileElement;
import org.openmuc.jasn1.compiler.pedefinitions.ProfileHeader;
import org.openmuc.jasn1.compiler.pedefinitions.ServicesList;
import org.openmuc.jasn1.compiler.pedefinitions.UInt15;
import org.openmuc.jasn1.compiler.pedefinitions.UInt16;
import org.openmuc.jasn1.compiler.pedefinitions.UInt8;

/**
 * Examples taken from Annex C, eUICC Profile Package: Interoperable Format Technical Specification, 2.0
 */
public class MobileTest {

    @Test
    public void decodeEncodeTest() throws Exception {

        String expected = "A1 820237 A0 05 80 00 81 01 01 A1 82022C 30 820228 62 10 82 02 7821 83 02 3F00 8B 01 0E C6 03 01020A 62 11 82 02 4121 83 02 2F05 8B 01 0F 80 01 03 88 01 28 62 0E 82 02 4121 83 02 2FE2 8B 01 0B 80 01 0A 81 0A 98109909002143658739 62 14 82 04 4221002683 02 2F00 8B 01 0A 80 02 0098 88 01 F0 81 1A 61184F10A0000000871002FF33FF01890000010050045553494D 62 11 82 04 42210025 83 02 2F06 8B 01 0A 80 02 01EF 81 1B 8001019000800102A406830101950108800158A40683010A950108 02 01 0A 81 16 800101A40683010195010880015AA40683010A950108 02 01 0F 81 0B 80015BA40683010A950108 02 01 1A 81 0A 800101900080015A9700 02 01 1B 81 16 800103A406830101950108800158A40683010A950108 02 01 0F 81 16 800111A40683010195010880014AA40683010A950108 02 01 0F 81 21 800103A406830101950108800158A40683010A950108840132A406830101950108 02 01 04 81 21 800101A406830101950108800102A406830181950108800158A40683010A950108 02 01 04 81 1B 800101900080011AA406830101950108800140A40683010A950108 02 01 0A 81 10 800101900080015AA40683010A950108 02 01 15 81 15 8001019000800118A40683010A9501088001429700 02 01 0E 81 10 800101A40683010195010880015A9700 02 01 15 81 16 800113A406830101950108800148A40683010A950108 02 01 0D 81 0B 80015EA40683010A950108 02 01 1A81 25 8001019000800102A010A406830101950108A406830102950108800158A40683010A950108 62 0E 82 02 4121 83 02 2F08 8B 01 0A 80 01 05"
                .replaceAll("\\s", "");

        byte[] code = DatatypeConverter.parseHexBinary(expected);

        ProfileElement rereadProfileElement = new ProfileElement();
        rereadProfileElement.decode(new ByteArrayInputStream(code), null);

        BerByteArrayOutputStream berByteArrayOutputStream2 = new BerByteArrayOutputStream(2048, true);
        rereadProfileElement.encode(berByteArrayOutputStream2);
        byte[] code2 = berByteArrayOutputStream2.getArray();
        Assert.assertArrayEquals(code, code2);

    }

    @Test
    public void header() throws Exception {
        ProfileElement headerProfileElement = new ProfileElement();

        ServicesList servicesList = new ServicesList();
        ProfileHeader.EUICCMandatoryGFSTEList GFSTEList = new ProfileHeader.EUICCMandatoryGFSTEList();
        GFSTEList.seqOf = Arrays.asList(new BerObjectIdentifier(new int[] { 2, 23, 143, 1, 2, 1 }),
                new BerObjectIdentifier(new int[] { 2, 23, 143, 1, 2, 4 }));

        servicesList.usim = new BerNull();
        servicesList.milenage = new BerNull();
        servicesList.javacard = new BerNull();
        headerProfileElement.header = new ProfileHeader(new UInt8(2), new UInt8(0),
                new BerUTF8String("SIMalliance Sample Profile"),
                new BerOctetString(DatatypeConverter.parseHexBinary("89019990001234567893")), null, servicesList,
                GFSTEList, null);

        BerByteArrayOutputStream berByteArrayOutputStream = new BerByteArrayOutputStream(2048, true);
        headerProfileElement.encode(berByteArrayOutputStream);
        byte[] code = berByteArrayOutputStream.getArray();
        ProfileElement rereadProfileElement = new ProfileElement();
        rereadProfileElement.decode(new ByteArrayInputStream(code), null);
        BerByteArrayOutputStream berByteArrayOutputStream2 = new BerByteArrayOutputStream(2048, true);
        rereadProfileElement.encode(berByteArrayOutputStream2);
        byte[] code2 = berByteArrayOutputStream2.getArray();
        Assert.assertArrayEquals(code, code2);

        String expected = "A0 48 80 01 02 81 01 00 82 1A 53494D616C6C69616E63652053616D706C652050726F66696C65 83 0A 89019990001234567893 A5 06 81 00 84 00 8B 00 A6 10 06 06 67810F010201 06 06 67810F010204"
                .replaceAll("\\s", "");
        Assert.assertEquals(expected, DatatypeConverter.printHexBinary(code));
    }

    private static PUKConfiguration createPUK(byte pukReference, String pukValue) {
        return createPUK(pukReference, pukValue, null, null);
    }

    private static PUKConfiguration createPUK(byte pukReference, String pukValue, Byte maxNumOfAttempts,
            Byte retryNumLeft) {
        return new PUKConfiguration(new PUKKeyReferenceValue(pukReference & 0xff),
                new BerOctetString(DatatypeConverter.parseHexBinary(pukValue)),
                maxNumOfAttempts != null && retryNumLeft != null ? new UInt8((maxNumOfAttempts << 4) | retryNumLeft)
                        : null);
    }

    @Test
    public void puk() throws Exception {

        ProfileElement pukProfileElement = new ProfileElement();

        PEPUKCodes.PukCodes pukCodes = new PEPUKCodes.PukCodes(Arrays.asList(
                createPUK((byte) 1, "3030303030303030", (byte) 9, (byte) 9), createPUK((byte) 2, "3132333435363738"),
                createPUK((byte) 0x81, "3132333435363738", (byte) 8, (byte) 8)));

        PEPUKCodes pepukCodes = new PEPUKCodes(new PEHeader(new BerNull(), new UInt15(2)), pukCodes);

        pukProfileElement.pukCodes = pepukCodes;

        BerByteArrayOutputStream berByteArrayOutputStream = new BerByteArrayOutputStream(2048, true);
        pukProfileElement.encode(berByteArrayOutputStream);
        byte[] code = berByteArrayOutputStream.getArray();
        System.out.println(HexConverter.toShortHexString(code));
        ProfileElement rereadProfileElement = new ProfileElement();
        rereadProfileElement.decode(new ByteArrayInputStream(code), null);
        BerByteArrayOutputStream berByteArrayOutputStream2 = new BerByteArrayOutputStream(2048, true);
        rereadProfileElement.encode(berByteArrayOutputStream2);
        byte[] code2 = berByteArrayOutputStream2.getArray();
        Assert.assertArrayEquals(code, code2);

        String expected = "A3 3F A0 05 80 00 81 01 02 A1 36 30 11 80 01 01 81 08 3030303030303030 82 02 0099 30 0D 80 01 02 81 08 3132333435363738 30 12 80 02 0081 81 08 3132333435363738 82 02 0088"
                .replaceAll("\\s", "");
        Assert.assertEquals(expected, DatatypeConverter.printHexBinary(code));
    }

    @Test
    public void genericFileManagement() throws Exception {

        ProfileElement genericFileManagementProfileElement = new ProfileElement();

        List<FileManagement.CHOICE> choices = new ArrayList<>();
        choices.add(createDf((short) 0x3f00, "7821", (byte) 0x0e, "01020A"));
        choices.addAll(createEf((short) 0x2f05, "4121", (byte) 0x0F, (short) 3, (short) 0x28));
        choices.addAll(createEf((short) 0x2fe2, "4121", (byte) 0x0B, (short) 0x0A, null, "98109909002143658739", null));
        choices.addAll(createEf((short) 0x2f00, "42210026", (byte) 0x0A, (short) 0x98, (short) 0xF0,
                "61184F10A0000000871002FF33FF01890000010050045553494D", null));
        choices.addAll(createEf((short) 0x2f06, "42210025", (byte) 0x0A, (short) 0x01EF, null,
                Arrays.asList(new FileRecordContent(null, "8001019000800102A406830101950108800158A40683010A950108"),
                        new FileRecordContent(10, "800101A40683010195010880015AA40683010A950108"),
                        new FileRecordContent(15, "80015BA40683010A950108"),
                        new FileRecordContent(26, "800101900080015A9700"),
                        new FileRecordContent(27, "800103A406830101950108800158A40683010A950108"),
                        new FileRecordContent(15, "800111A40683010195010880014AA40683010A950108"),
                        new FileRecordContent(15, "800103A406830101950108800158A40683010A950108840132A406830101950108"),
                        new FileRecordContent(4, "800101A406830101950108800102A406830181950108800158A40683010A950108"),
                        new FileRecordContent(4, "800101900080011AA406830101950108800140A40683010A950108"),
                        new FileRecordContent(10, "800101900080015AA40683010A950108"),
                        new FileRecordContent(21, "8001019000800118A40683010A9501088001429700"),
                        new FileRecordContent(14, "800101A40683010195010880015A9700"),
                        new FileRecordContent(21, "800113A406830101950108800148A40683010A950108"),
                        new FileRecordContent(13, "80015EA40683010A950108"), new FileRecordContent(26,
                                "8001019000800102A010A406830101950108A406830102950108800158A40683010A950108"))));
        choices.addAll(createEf((short) 0x2f08, "4121", (byte) 0x0A, (short) 5));

        genericFileManagementProfileElement.genericFileManagement = new PEGenericFileManagement(
                new PEHeader(new BerNull(), new UInt15(1)),
                new PEGenericFileManagement.FileManagementCMD(Arrays.asList(new FileManagement(choices))

                ));
        BerByteArrayOutputStream berByteArrayOutputStream = new BerByteArrayOutputStream(2048, true);
        genericFileManagementProfileElement.encode(berByteArrayOutputStream);
        byte[] code = berByteArrayOutputStream.getArray();
        System.out.println(DatatypeConverter.printHexBinary(code));
        ProfileElement rereadProfileElement = new ProfileElement();
        rereadProfileElement.decode(new ByteArrayInputStream(code), null);
        BerByteArrayOutputStream berByteArrayOutputStream2 = new BerByteArrayOutputStream(2048, true);
        rereadProfileElement.encode(berByteArrayOutputStream2);
        byte[] code2 = berByteArrayOutputStream2.getArray();
        Assert.assertArrayEquals(code, code2);

        String expected = "A1 820237 A0 05 80 00 81 01 01 A1 82022C 30 820228 62 10 82 02 7821 83 02 3F00 8B 01 0E C6 03 01020A 62 11 82 02 4121 83 02 2F05 8B 01 0F 80 01 03 88 01 28 62 0E 82 02 4121 83 02 2FE2 8B 01 0B 80 01 0A 81 0A 98109909002143658739 62 14 82 04 4221002683 02 2F00 8B 01 0A 80 02 0098 88 01 F0 81 1A 61184F10A0000000871002FF33FF01890000010050045553494D 62 11 82 04 42210025 83 02 2F06 8B 01 0A 80 02 01EF 81 1B 8001019000800102A406830101950108800158A40683010A950108 02 01 0A 81 16 800101A40683010195010880015AA40683010A950108 02 01 0F 81 0B 80015BA40683010A950108 02 01 1A 81 0A 800101900080015A9700 02 01 1B 81 16 800103A406830101950108800158A40683010A950108 02 01 0F 81 16 800111A40683010195010880014AA40683010A950108 02 01 0F 81 21 800103A406830101950108800158A40683010A950108840132A406830101950108 02 01 04 81 21 800101A406830101950108800102A406830181950108800158A40683010A950108 02 01 04 81 1B 800101900080011AA406830101950108800140A40683010A950108 02 01 0A 81 10 800101900080015AA40683010A950108 02 01 15 81 15 8001019000800118A40683010A9501088001429700 02 01 0E 81 10 800101A40683010195010880015A9700 02 01 15 81 16 800113A406830101950108800148A40683010A950108 02 01 0D 81 0B 80015EA40683010A950108 02 01 1A81 25 8001019000800102A010A406830101950108A406830102950108800158A40683010A950108 62 0E 82 02 4121 83 02 2F08 8B 01 0A 80 01 05"
                .replaceAll("\\s", "");
        Assert.assertEquals(expected, DatatypeConverter.printHexBinary(code));
    }

    private static class FileRecordContent {

        public Integer fillFileOffset;

        public String fillFileContent;

        public FileRecordContent(Integer fillFileOffset, String fillFileContent) {
            this.fillFileOffset = fillFileOffset;
            this.fillFileContent = fillFileContent;
        }
    }

    /**
     * Converts a unsigned short to a byte array.
     *
     * @param num
     *            The number.
     * @return byte array of length 2.
     */
    public static byte[] unsignedShortToByteArray(int num) {
        if (num < 128) {
            return new byte[] { (byte) num };
        }
        byte hiByte = (byte) ((num & 0xFF00) >> 8);
        byte loByte = (byte) (num - (hiByte << 8));
        return new byte[] { hiByte, loByte };
    }

    private static List<FileManagement.CHOICE> createEf(short fileId, String fileDescriptor, byte arrReference,
            Short efFileSize) {
        return createEf(fileId, fileDescriptor, arrReference, efFileSize, null, null, null, null);
    }

    private static List<FileManagement.CHOICE> createEf(short fileId, String fileDescriptor, byte arrReference,
            Short efFileSize, Short shortEfId) {
        return createEf(fileId, fileDescriptor, arrReference, efFileSize, shortEfId, null, null, null);
    }

    private static List<FileManagement.CHOICE> createEf(short fileId, String fileDescriptor, byte arrReference,
            Short efFileSize, Short shortEfId, String fillFileContent, Integer fillFileOffset) {
        return createEf(fileId, fileDescriptor, arrReference, efFileSize, shortEfId, null, fillFileContent,
                fillFileOffset);
    }

    private static List<FileManagement.CHOICE> createEf(short fileId, String fileDescriptor, byte arrReference,
            Short efFileSize, Short shortEfId, String linkPath, String fillFileContent, Integer fillFileOffset) {
        List<FileManagement.CHOICE> fileManagementSubChoices = new ArrayList<>();
        fileManagementSubChoices.add(new FileManagement.CHOICE(null,
                createFcp(fileId, fileDescriptor, arrReference, efFileSize, null, shortEfId, linkPath), null, null));
        if (fillFileOffset != null) {
            fileManagementSubChoices.add(new FileManagement.CHOICE(null, null, new UInt16(fillFileOffset), null));
        }
        if (fillFileContent != null) {
            fileManagementSubChoices.add(new FileManagement.CHOICE(null, null, null,
                    new BerOctetString(HexConverter.fromShortHexString(fillFileContent))));
        }
        return fileManagementSubChoices;
    }

    private static List<FileManagement.CHOICE> createEf(short fileId, String fileDescriptor, byte arrReference,
            Short efFileSize, Short shortEfId, List<FileRecordContent> fillFileRecordContents) {
        final List<FileManagement.CHOICE> fileManagementChoices = createEf(fileId, fileDescriptor, arrReference,
                efFileSize, shortEfId);
        if (fillFileRecordContents != null) {
            for (FileRecordContent fileRecordContent : fillFileRecordContents) {
                if (fileRecordContent.fillFileOffset != null) {
                    fileManagementChoices.add(
                            new FileManagement.CHOICE(null, null, new UInt16(fileRecordContent.fillFileOffset), null));
                }
                fileManagementChoices.add(new FileManagement.CHOICE(null, null, null,
                        new BerOctetString(DatatypeConverter.parseHexBinary(fileRecordContent.fillFileContent))));
            }
        }
        return fileManagementChoices;
    }

    private static FileManagement.CHOICE createDf(short fileId, String fileDescriptor, byte arrReference,
            String pinStatusTemplateDo) {
        return new FileManagement.CHOICE(null,
                createFcp(fileId, fileDescriptor, arrReference, null, pinStatusTemplateDo, null, null), null, null);
    }

    private static Fcp createFcp(short fileId, String fileDescriptor, byte arrReference, Short efFileSize,
            String pinStatusTemplateDo, Short shortEfId, String linkPath) {

        return new Fcp(new BerOctetString(DatatypeConverter.parseHexBinary(fileDescriptor)),
                new BerOctetString(unsignedShortToByteArray(fileId)), null, null,
                new BerOctetString(new byte[] { arrReference }),
                efFileSize != null ? new BerOctetString(unsignedShortToByteArray(efFileSize)) : null,
                pinStatusTemplateDo != null ? new BerOctetString(DatatypeConverter.parseHexBinary(pinStatusTemplateDo))
                        : null,
                shortEfId != null ? new BerOctetString(new byte[] { shortEfId.byteValue() }) : null, null,
                linkPath != null ? new BerOctetString(DatatypeConverter.parseHexBinary(linkPath)) : null);

    }
}
