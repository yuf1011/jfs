package jfs.authentication.ntlm.message;

import jfs.authentication.ntlm.message.basic.VersionStructure;
import jfs.common.IDecodable;
import jfs.common.IEncodable;

public class AuthenticateMessage implements IDecodable{

	public static final short FIXED_SIZE = 88;
	
	public byte Signature[];
	public int MessageType;
	public short LmChallengeResponseLen;
	public short LmChallengeResponseMaxLen;
	public int LmChallengeResponseBufferOffset;
	public short NtChallengeResponseLen;
	public short NtChallengeResponseMaxLen;
	public int NtChallengeResponseBufferOffset;
	public short DomainNameLen;
	public short DomainNameMaxLen;
	public int DomainNameBufferOffset;
	public short UserNameLen;
	public short UserNameMaxLen;
	public int UserNameBufferOffset;
	public short WorkstationLen;
	public short WorkstationMaxLen;
	public int WorkstationBufferOffset;
	public short EncryptedRandomSessionKeyLen;
	public short EncryptedRandomSessionKeyMaxLen;
	public int EncryptedRandomSessionKeyBufferOffset;
	public int NegotiateFlags;
	public VersionStructure Version;
	public byte MIC[];
	IEncodable LmChallengeResponse;
	IEncodable NtChallengeResponse;
	String DomainName;
	String UserName;
	String Workstation;
	byte EncryptedRandomSessionKey[];
	
	@Override
	public int fromBytes(byte[] data, int offset, int length) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int size() {
		return FIXED_SIZE;
	}

}
