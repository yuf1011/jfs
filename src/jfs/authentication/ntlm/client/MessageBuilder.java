package jfs.authentication.ntlm.client;

import jfs.authentication.ntlm.message.NegotiateMessage;
import jfs.authentication.ntlm.message.basic.NegotiateFlagValues;

public class MessageBuilder {

	public static NegotiateMessage ComposeNTLMNegotiateMessage() {
		NegotiateMessage message = new NegotiateMessage();
		message.NegotiateFlags |= NegotiateFlagValues.NTLMSSP_NEGOTIATE_56
				| NegotiateFlagValues.NTLMSSP_NEGOTIATE_128
				| NegotiateFlagValues.NTLMSSP_NEGOTIATE_KEY_EXCH
				| NegotiateFlagValues.NTLMSSP_NEGOTIATE_NTLM
				| NegotiateFlagValues.NTLMSSP_NEGOTIATE_LM_KEY
				| NegotiateFlagValues.NTLM_NEGOTIATE_OEM;
		return message;
	}
}
