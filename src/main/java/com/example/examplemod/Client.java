package com.example.examplemod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Thread {

    private Socket client;
    private final EntityPlayer player;

    public Client(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 8000);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String inMessage;
            while (!client.isClosed()) {
                if ((inMessage = in.readLine()) != null) {
                    player.sendMessage(new TextComponentString(inMessage));
                }
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        try {
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException ignored) {}
    }
}

