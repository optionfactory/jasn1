/**
 * This class file was automatically generated by jASN1 v1.7.1 (http://www.openmuc.org)
 */

package org.openmuc.jasn1.compiler.iso8823_presentation;

import java.io.IOException;
import java.io.EOFException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import org.openmuc.jasn1.ber.*;
import org.openmuc.jasn1.ber.types.*;
import org.openmuc.jasn1.ber.types.string.*;


public class PDVList {

	public static class PresentationDataValues {

		public byte[] code = null;
		public BerAnyNoDecode singleASN1Type = null;
		public BerOctetString octetAligned = null;
		public BerBitString arbitrary = null;
		
		public PresentationDataValues() {
		}

		public PresentationDataValues(byte[] code) {
			this.code = code;
		}

		public PresentationDataValues(BerAnyNoDecode singleASN1Type, BerOctetString octetAligned, BerBitString arbitrary) {
			this.singleASN1Type = singleASN1Type;
			this.octetAligned = octetAligned;
			this.arbitrary = arbitrary;
		}

		public int encode(BerByteArrayOutputStream os) throws IOException {

			if (code != null) {
				for (int i = code.length - 1; i >= 0; i--) {
					os.write(code[i]);
				}
				return code.length;
			}

			int codeLength = 0;
			int sublength;

			if (arbitrary != null) {
				codeLength += arbitrary.encode(os, false);
				// write tag: CONTEXT_CLASS, PRIMITIVE, 2
				os.write(0x82);
				codeLength += 1;
				return codeLength;
			}
			
			if (octetAligned != null) {
				codeLength += octetAligned.encode(os, false);
				// write tag: CONTEXT_CLASS, PRIMITIVE, 1
				os.write(0x81);
				codeLength += 1;
				return codeLength;
			}
			
			if (singleASN1Type != null) {
				sublength = singleASN1Type.encode(os);
				codeLength += sublength;
				codeLength += BerLength.encodeLength(os, sublength);
				// write tag: CONTEXT_CLASS, CONSTRUCTED, 0
				os.write(0xA0);
				codeLength += 1;
				return codeLength;
			}
			
			throw new IOException("Error encoding BerChoice: No item in choice was selected.");
		}

		public int decode(InputStream is) throws IOException {
			return decode(is, null);
		}

		public int decode(InputStream is, BerTag berTag) throws IOException {

			int codeLength = 0;
			BerTag passedTag = berTag;

			if (berTag == null) {
				berTag = new BerTag();
				codeLength += berTag.decode(is);
			}

			if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.CONSTRUCTED, 0)) {
				BerLength length = new BerLength();
				codeLength += length.decode(is);
				singleASN1Type = new BerAnyNoDecode();
				codeLength += singleASN1Type.decode(is, length.val);
				return codeLength;
			}

			if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 1)) {
				octetAligned = new BerOctetString();
				codeLength += octetAligned.decode(is, false);
				return codeLength;
			}

			if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 2)) {
				arbitrary = new BerBitString();
				codeLength += arbitrary.decode(is, false);
				return codeLength;
			}

			if (passedTag != null) {
				return 0;
			}

			throw new IOException("Error decoding BerChoice: Tag matched to no item.");
		}

		public void encodeAndSave(int encodingSizeGuess) throws IOException {
			BerByteArrayOutputStream os = new BerByteArrayOutputStream(encodingSizeGuess);
			encode(os);
			code = os.getArray();
		}

		public String toString() {
			if (singleASN1Type != null) {
				return "CHOICE{singleASN1Type: " + singleASN1Type + "}";
			}

			if (octetAligned != null) {
				return "CHOICE{octetAligned: " + octetAligned + "}";
			}

			if (arbitrary != null) {
				return "CHOICE{arbitrary: " + arbitrary + "}";
			}

			return "unknown";
		}

	}

	public static final BerTag tag = new BerTag(BerTag.UNIVERSAL_CLASS, BerTag.CONSTRUCTED, 16);

	public byte[] code = null;
	public TransferSyntaxName transferSyntaxName = null;
	public PresentationContextIdentifier presentationContextIdentifier = null;
	public PresentationDataValues presentationDataValues = null;
	
	public PDVList() {
	}

	public PDVList(byte[] code) {
		this.code = code;
	}

	public PDVList(TransferSyntaxName transferSyntaxName, PresentationContextIdentifier presentationContextIdentifier, PresentationDataValues presentationDataValues) {
		this.transferSyntaxName = transferSyntaxName;
		this.presentationContextIdentifier = presentationContextIdentifier;
		this.presentationDataValues = presentationDataValues;
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

		int codeLength = 0;
		codeLength += presentationDataValues.encode(os);
		
		codeLength += presentationContextIdentifier.encode(os, true);
		
		if (transferSyntaxName != null) {
			codeLength += transferSyntaxName.encode(os, true);
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
		int subCodeLength = 0;
		BerTag berTag = new BerTag();

		if (withTag) {
			codeLength += tag.decodeAndCheck(is);
		}

		BerLength length = new BerLength();
		codeLength += length.decode(is);

		int totalLength = length.val;
		if (totalLength == -1) {
			subCodeLength += berTag.decode(is);

			if (berTag.tagNumber == 0 && berTag.tagClass == 0 && berTag.primitive == 0) {
				int nextByte = is.read();
				if (nextByte != 0) {
					if (nextByte == -1) {
						throw new EOFException("Unexpected end of input stream.");
					}
					throw new IOException("Decoded sequence has wrong end of contents octets");
				}
				codeLength += subCodeLength + 1;
				return codeLength;
			}
			if (berTag.equals(TransferSyntaxName.tag)) {
				transferSyntaxName = new TransferSyntaxName();
				subCodeLength += transferSyntaxName.decode(is, false);
				subCodeLength += berTag.decode(is);
			}
			if (berTag.tagNumber == 0 && berTag.tagClass == 0 && berTag.primitive == 0) {
				int nextByte = is.read();
				if (nextByte != 0) {
					if (nextByte == -1) {
						throw new EOFException("Unexpected end of input stream.");
					}
					throw new IOException("Decoded sequence has wrong end of contents octets");
				}
				codeLength += subCodeLength + 1;
				return codeLength;
			}
			if (berTag.equals(PresentationContextIdentifier.tag)) {
				presentationContextIdentifier = new PresentationContextIdentifier();
				subCodeLength += presentationContextIdentifier.decode(is, false);
				subCodeLength += berTag.decode(is);
			}
			if (berTag.tagNumber == 0 && berTag.tagClass == 0 && berTag.primitive == 0) {
				int nextByte = is.read();
				if (nextByte != 0) {
					if (nextByte == -1) {
						throw new EOFException("Unexpected end of input stream.");
					}
					throw new IOException("Decoded sequence has wrong end of contents octets");
				}
				codeLength += subCodeLength + 1;
				return codeLength;
			}
			presentationDataValues = new PresentationDataValues();
			int choiceDecodeLength = presentationDataValues.decode(is, berTag);
			if (choiceDecodeLength != 0) {
				subCodeLength += choiceDecodeLength;
				subCodeLength += berTag.decode(is);
			}
			else {
				presentationDataValues = null;
			}

			int nextByte = is.read();
			if (berTag.tagNumber != 0 || berTag.tagClass != 0 || berTag.primitive != 0
			|| nextByte != 0) {
				if (nextByte == -1) {
					throw new EOFException("Unexpected end of input stream.");
				}
				throw new IOException("Decoded sequence has wrong end of contents octets");
			}
			codeLength += subCodeLength + 1;
			return codeLength;
		}

		codeLength += totalLength;

		subCodeLength += berTag.decode(is);
		if (berTag.equals(TransferSyntaxName.tag)) {
			transferSyntaxName = new TransferSyntaxName();
			subCodeLength += transferSyntaxName.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		
		if (berTag.equals(PresentationContextIdentifier.tag)) {
			presentationContextIdentifier = new PresentationContextIdentifier();
			subCodeLength += presentationContextIdentifier.decode(is, false);
			subCodeLength += berTag.decode(is);
		}
		else {
			throw new IOException("Tag does not match the mandatory sequence element tag.");
		}
		
		presentationDataValues = new PresentationDataValues();
		subCodeLength += presentationDataValues.decode(is, berTag);
		if (subCodeLength == totalLength) {
			return codeLength;
		}
		throw new IOException("Unexpected end of sequence, length tag: " + totalLength + ", actual sequence length: " + subCodeLength);

		
	}

	public void encodeAndSave(int encodingSizeGuess) throws IOException {
		BerByteArrayOutputStream os = new BerByteArrayOutputStream(encodingSizeGuess);
		encode(os, false);
		code = os.getArray();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("SEQUENCE{");
		boolean firstSelectedElement = true;
		if (transferSyntaxName != null) {
			sb.append("transferSyntaxName: ").append(transferSyntaxName);
			firstSelectedElement = false;
		}
		
		if (!firstSelectedElement) {
			sb.append(", ");
		}
		sb.append("presentationContextIdentifier: ").append(presentationContextIdentifier);
		
		sb.append(", ");
		sb.append("presentationDataValues: ").append(presentationDataValues);
		
		sb.append("}");
		return sb.toString();
	}

}

