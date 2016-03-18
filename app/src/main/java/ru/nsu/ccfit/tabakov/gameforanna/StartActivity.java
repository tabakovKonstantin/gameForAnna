package ru.nsu.ccfit.tabakov.gameforanna;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.felhr.usbserial.*;

/**
 * Created by Константин on 13.08.2015.
 */
public class StartActivity extends Activity implements View.OnClickListener {

    private Button buttonOk = null;
    private EditText name = null;
    private ArrayList<String> list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        initComponents();
        test();
        setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button: {
                Random rand = new Random();
                int start = 0;
                int end = list.size() - 1;
                int randomIndex = rand.nextInt((end - start) + 1) + start;
                Intent intent =  new Intent(this, Answer.class);
                intent.putExtra("answer", name.getText().toString().concat(" ").concat(list.get(randomIndex)));
                startActivity(intent);
            break;
            }
        }
    }

    private void initComponents() {
        list = new ArrayList<String>(Arrays.asList(", ты какашка",
                                                    ", ты старый пердун",
                ", ты восхитительно прекрасный человек",
                ", человек справа от тебя -- идиот",
                ", ты кумир поколений",
                ", человек рядом с тобой -- твой повелитель",
                ", ты упорот до невозможности",
                ", ты Сергей Зверев",
                ", ты хороший",
                ", ты смешной",
                ", ты гей",
                ", ты  самый, что ни на есть гей",
                ", все вокруг будут выполнять твое желание",
                ", ты должен танцевать танец маленьких утят",
                ", ты трусишка",
                ", ты зануда",
                ", жил-был, ты поживал, фигнёй страдал. И вдруг стал ты разумным делом заниматься - перестал ссаться",
                ", будь же человеком, свинтус ",
                ", спой чунга-чангу, трясся попой",
                ", изволь сказать, царь. Где твои мозги?",
                ", спой песенку, которую ты терпеть не можешь",
                ", сочини песенку про своего соседа слева и изволь спеть её, мать твою",
                ", сегодня тебе повезло - ты выиграл право управлять слева сидящим человеком 24 часа",
                ", раздвинь руки в стороны и закричи: Я птичка! Я лечу!, осознай, что ты - идиот",
                ", раскажи свой самый сокровенный секрет, прими издевательства на себя",
                ", сегодня ты самый умный человек в обществе, поздравляю!",
                ", ты обманщик",
                ", ты транссвестит",
                ", ты полоумный",
                ", ты мишка",
                ", ты садист",
                ", сегодня ты исчезнешь, когда луна взойдёт ",
                ", умри, гад",
                ", ты самый большой зануда",
                "ты повелитель вселенной"));
        buttonOk = (Button) findViewById(R.id.button);
        name = (EditText) findViewById(R.id.editText);
    }

    private void setListener(View.OnClickListener listener) {
         buttonOk.setOnClickListener(listener);
    }

    private void test() {
        try {
            UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

            HashMap usbDevices = usbManager.getDeviceList();
            Set<Map.Entry> usbDevicesSet = usbDevices.entrySet();
            for ( Map.Entry<String, UsbDevice> entry : usbDevicesSet) {
                UsbDevice usbDevice = entry.getValue();
                int deviceVID = usbDevice.getVendorId();
                if (deviceVID == 0x2341) { //Arduino Vendor ID
                    Log.i("aaa", "it ard" );
                    PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0,
                    new Intent("com.android.example.USB_PERMISSION"), 0);
                    usbManager.requestPermission(usbDevice, mPermissionIntent);

                    UsbDeviceConnection connection = usbManager.openDevice(usbDevice);
                    UsbSerialDevice serialPort = UsbSerialDevice.createUsbSerialDevice(usbDevice, connection);
                    if (serialPort != null) {
                        if (serialPort.open()) { //Set Serial Connection Parameters.
                            serialPort.setBaudRate(9600);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialPort.read(mCallback);

                            
                            Log.i("aaa", "open port");

                        } else {
                            Log.e("aaa", "PORT NOT OPEN");
                        }
                    } else {
                        Log.e("aaa", "PORT IS NULL");
                    }

                }

            }


         } catch (Exception e) {
            Log.e("aaa", e.getStackTrace().toString());
            name.setText(e.getMessage());
        }

    }

    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {


        @Override
        public void onReceivedData(byte[] bytes) {
            String data = null;
            try {
                data = new String(bytes, "UTF-8");
//                data.concat("/n");
                Log.i("aaa", data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    };


    }





