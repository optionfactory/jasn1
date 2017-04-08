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

public class PrepareDownloadResponse {

	public byte[] code = null;
	public static final BerTag tag = new BerTag(BerTag.CONTEXT_CLASS, BerTag.CONSTRUCTED, 33);

	public PrepareDownloadResponseOk downloadResponseOk = null;
	public PrepareDownloadResponseError downloadResponseError = null;
	
	public PrepareDownloadResponse() {
	}

	public PrepareDownloadResponse(byte[] code) {
		this.code = code;
	}

	public PrepareDownloadResponse(PrepareDownloadResponseOk downloadResponseOk, PrepareDownloadResponseError downloadResponseError) {
		this.downloadResponseOk = downloadResponseOk;
		this.downloadResponseError = downloadResponseError;
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
		if (downloadResponseError != null) {
			codeLength += downloadResponseError.encode(os, false);
			// write tag: CONTEXT_CLASS, CONSTRUCTED, 1
			os.write(0xA1);
			codeLength += 1;
			codeLength += BerLength.encodeLength(os, codeLength);
			if (withTag) {
				codeLength += tag.encode(os);
			}
			return codeLength;
		}
		
		if (downloadResponseOk != null) {
			codeLength += downloadResponseOk.encode(os, false);
			// write tag: CONTEXT_CLASS, CONSTRUCTED, 0
			os.write(0xA0);
			codeLength += 1;
			codeLength += BerLength.encodeLength(os, codeLength);
			if (withTag) {
				codeLength += tag.encode(os);
			}
			return codeLength;
		}
		
		throw new IOException("Error encoding BerChoice: No item in choice was selected.");
	}

	public int decode(InputStream is) throws IOException {
		return decode(is, true);
	}

	public int decode(InputStream is, boolean withTag) throws IOException {
		int codeLength = 0;
		BerLength length = new BerLength();
		BerTag berTag = new BerTag();

		if (withTag) {
			codeLength += tag.decodeAndCheck(is);
		}

		codeLength += length.decode(is);
		codeLength += berTag.decode(is);

		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.CONSTRUCTED, 0)) {
			downloadResponseOk = new PrepareDownloadResponseOk();
			codeLength += downloadResponseOk.decode(is, false);
			return codeLength;
		}

		if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.CONSTRUCTED, 1)) {
			downloadResponseError = new PrepareDownloadResponseError();
			codeLength += downloadResponseError.decode(is, false);
			return codeLength;
		}

		throw new IOException("Error decoding BerChoice: Tag matched to no item.");
	}

	public void encodeAndSave(int encodingSizeGuess) throws IOException {
		BerByteArrayOutputStream os = new BerByteArrayOutputStream(encodingSizeGuess);
		encode(os, false);
		code = os.getArray();
	}

	public String toString() {
		if (downloadResponseOk != null) {
			return "CHOICE{downloadResponseOk: " + downloadResponseOk + "}";
		}

		if (downloadResponseError != null) {
			return "CHOICE{downloadResponseError: " + downloadResponseError + "}";
		}

		return "unknown";
	}

}

