package jfs.smb2.test;

import java.io.IOException;

import jfs.smb2.client.ProtocolClient;

public class Test {

	public static void main(String[] args) {
		ProtocolClient client = new ProtocolClient();
		try {
			client.connect("yufan-vm-blue");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
