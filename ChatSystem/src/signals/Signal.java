package signals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Signal implements Serializable {

       
    
    public static byte[] toByteArray(Signal s) throws SignalTooBigException, IOException {

        byte[] result = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        
        oos.writeObject(s);
        
        if (baos.size() > 1024) {
            throw new SignalTooBigException("Signal too big");
        }
        
        result = baos.toByteArray();
        return result;
    }

    public static Signal fromByteArray(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        
        return ((Signal) ois.readObject());
    }
}
