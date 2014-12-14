package jfs.authentication.ntlm.message.basic;

public class NegotiateFlagValues {
	public static final int NTLMSSP_NEGOTIATE_56 = 1 << 31;
	public static final int NTLMSSP_NEGOTIATE_KEY_EXCH = 1 << 30;
	public static final int NTLMSSP_NEGOTIATE_128 = 1 << 29;
	public static final int r1 = 1 << 28;
	public static final int r2= 1 << 27;
	public static final int r3 = 1 << 26;
	public static final int NTLMSSP_NEGOTIATE_VERSION = 1 << 25;
	public static final int r4 = 1 << 24;
	public static final int NTLMSSP_NEGOTIATE_TARGET_INFO = 1 << 23;
	public static final int NTLMSSP_REQUEST_NON_NT_SESSION_KEY = 1 << 22;
	public static final int r5 = 1 << 21;
	public static final int NTLMSSP_NEGOTIATE_IDENTIFY = 1 << 20;
	public static final int NTLMSSP_NEGOTIATE_EXTENDED_SESSIONSECURITY = 1 << 19;
	public static final int r6 = 1 << 18;
	public static final int NTLMSSP_TARGET_TYPE_SERVER = 1 << 17;
	public static final int NTLMSSP_TARGET_TYPE_DOMAIN = 1 << 16;
	public static final int NTLMSSP_NEGOTIATE_ALWAYS_SIGN = 1 << 15;
	public static final int r7 = 1 << 14;
	public static final int NTLMSSP_NEGOTIATE_OEM_WORKSTATION_SUPPLIED = 1 << 13;
	public static final int NTLMSSP_NEGOTIATE_OEM_DOMAIN_SUPPLIED = 1 << 12;
	public static final int J = 1 << 11;
	public static final int r8 = 1 << 10;
	public static final int NTLMSSP_NEGOTIATE_NTLM = 1 << 9;
	public static final int r9 = 1 << 8;
	public static final int NTLMSSP_NEGOTIATE_LM_KEY = 1 << 7;
	public static final int NTLMSSP_NEGOTIATE_DATAGRAM = 1 << 6;
	public static final int NTLMSSP_NEGOTIATE_SEAL = 1 << 5;
	public static final int NTLMSSP_NEGOTIATE_SIGN = 1 << 4;
	public static final int r10 = 1 << 3;
	public static final int NTLMSSP_REQUEST_TARGET = 1 << 2;
	public static final int NTLM_NEGOTIATE_OEM = 1 << 1;
	public static final int NTLMSSP_NEGOTIATE_UNICODE = 1 << 0;
}
