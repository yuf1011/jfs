package jfs.authentication.ntlm.message.basic;

import java.io.IOException;

import jfs.common.IEncodable;

public class Ntlmv2Response implements IEncodable{
	public static final short FIXED_SIZE = 16;
	
	public byte Response[];
	public Ntlmv2ClientChanllenge ClientChallenge;
		
	@Override
	public byte[] toBytes() throws IOException {
		byte retBytes[] = new byte[size()];
		byte ClientChallengeBytes[] = ClientChallenge.toBytes();
		System.arraycopy(Response, 0, retBytes, 0, 16);
		System.arraycopy(ClientChallengeBytes, 0, retBytes, 16, ClientChallenge.size());
		return retBytes;
	}

	@Override
	public int size() {
		return FIXED_SIZE + ClientChallenge.size();
	}

}
