package py.com.arquitickets.utils;

import org.springframework.http.HttpStatus;

public class Respuestas {

    private int status;
    private String message;
    private Object data;  // Campo adicional para los datos

    // Constructor para respuestas generales (con solo mensaje)
    public Respuestas(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.data = null;
    }

    // Constructor para respuestas con mensaje y datos adicionales
    public Respuestas(HttpStatus status, String message, Object data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    // Getters y setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

