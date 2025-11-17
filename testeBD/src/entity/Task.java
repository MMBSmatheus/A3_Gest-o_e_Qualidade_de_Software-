package entity;

public class Task {
    public int id;
    public int user_id;
    public String description;
    public String status;

            // Método para converter um objeto Users em um array de objetos
            public Object[] toArray() {
                return new Object[]{id, user_id, description,status};
            }
}
