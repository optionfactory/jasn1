package org.openmuc.jasn1.ber;

import java.io.IOException;

public interface BerEncodable {

    public int encode(BerByteArrayOutputStream os) throws IOException;

}
