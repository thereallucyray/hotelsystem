package csi3471.group5.store;

import csi3471.group5.Hotel;
import csi3471.group5.RoomType;
import csi3471.group5.db.DBSerde;
import csi3471.group5.db.DBStore;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RoomTypeStore extends DBStore<RoomType,RoomTypeStore> {

    @Override
    public String getFilename() {
        return "RoomType";
    }

    @Override
    public DBSerde<RoomType> getSerde() {
        return new DBSerde<RoomType>() {
            @Override
            public ArrayList<String> serialize(RoomType obj) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(Boolean.toString(obj.isSmoking()));
                list.add(Integer.toString(obj.getNumBeds()));
                list.add(obj.getQuality().toString());
                list.add(Double.toString(obj.getPrice()));
                return list;
            }
            @Override
            public RoomType deserialize(String[] s) {
                RoomType r = new RoomType();
                r.setSmoking(Boolean.parseBoolean(s[0]));
                r.setNumBeds(Integer.parseInt(s[1]));
                r.setQuality(Hotel.qualityDesc.valueOf(s[2]));
                r.setPrice(Double.parseDouble(s[3]));
                return r;
            }

            @Override
            public void resolveConnections(RoomType obj) {
                new RoomStore().resolve(obj.getRoomList());
            }
        };
    }
}
