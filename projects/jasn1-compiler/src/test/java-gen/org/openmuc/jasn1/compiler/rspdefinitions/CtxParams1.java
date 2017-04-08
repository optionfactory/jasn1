/**
 * This class file was automatically generated by jASN1 v1.7.1 (http://www.openmuc.org)
 */

package org.openmuc.jasn1.compiler.rspdefinitions;

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

import org.openmuc.jasn1.compiler.pkix1explicit88.*;
import org.openmuc.jasn1.compiler.pkix1implicit88.*;

public class CtxParams1 {

	public byte[] code = null;
	public CtxParamsForCommonAuthentication ctxParamsForCommonAuthentication = null;
	
	public CtxParams1() {
	}

	public CtxParams1(byte[] code) {
		this.code = code;
	}

	public CtxParams1(CtxParamsForCommonAuthentication ctxParamsForCommonAuthentication) {
		this.ctxParamsForCommonAuthentication = ctxParamsForCommonAuthentication;
	}

	public int encode(BerByteArrayOutputStream os) throws IOException {

		if (code != null) {
			for (int i = code.length - 1; i >= 0; i--) {
				os.write(code[i]);
			}
			return code.length;
		}

		int codeLength = 0;
		if (ctxParamsForCommonAuthentication != null) {
			codeLength += ctxParamsForCommonAuthentication.encode(os, false);
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
			ctxParamsForCommonAuthentication = new CtxParamsForCommonAuthentication();
			codeLength += ctxParamsForCommonAuthentication.decode(is, false);
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
		if (ctxParamsForCommonAuthentication != null) {
			return "CHOICE{ctxParamsForCommonAuthentication: " + ctxParamsForCommonAuthentication + "}";
		}

		return "unknown";
	}

}

