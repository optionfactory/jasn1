/**
 * This class file was automatically generated by jASN1 v1.7.1 (http://www.openmuc.org)
 */

package org.openmuc.jasn1.compiler.pkix1explicit88;

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

import org.openmuc.jasn1.compiler.pkix1implicit88.*;
import org.openmuc.jasn1.compiler.rspdefinitions.*;

public class PrivateDomainName {

	public byte[] code = null;
	public BerNumericString numeric = null;
	public BerPrintableString printable = null;
	
	public PrivateDomainName() {
	}

	public PrivateDomainName(byte[] code) {
		this.code = code;
	}

	public PrivateDomainName(BerNumericString numeric, BerPrintableString printable) {
		this.numeric = numeric;
		this.printable = printable;
	}

	public int encode(BerByteArrayOutputStream os) throws IOException {

		if (code != null) {
			for (int i = code.length - 1; i >= 0; i--) {
				os.write(code[i]);
			}
			return code.length;
		}

		int codeLength = 0;
		if (printable != null) {
			codeLength += printable.encode(os, true);
			return codeLength;
		}
		
		if (numeric != null) {
			codeLength += numeric.encode(os, true);
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

		if (berTag.equals(BerNumericString.tag)) {
			numeric = new BerNumericString();
			codeLength += numeric.decode(is, false);
			return codeLength;
		}

		if (berTag.equals(BerPrintableString.tag)) {
			printable = new BerPrintableString();
			codeLength += printable.decode(is, false);
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
		if (numeric != null) {
			return "CHOICE{numeric: " + numeric + "}";
		}

		if (printable != null) {
			return "CHOICE{printable: " + printable + "}";
		}

		return "unknown";
	}

}
