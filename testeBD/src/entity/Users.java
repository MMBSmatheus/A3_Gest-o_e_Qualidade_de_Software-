package entity;

import java.util.Vector;

public class Users {
    public int id;
    public String username;
    public String password;

        // Método para converter um objeto Users em um array de objetos
        public Object[] toArray() {
            return new Object[]{id, username};
        }
}

