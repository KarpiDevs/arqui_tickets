package py.com.arquitickets.utils;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class RespuestasDashboard {

        private HttpStatus status;
        private String message;
        private Map<String, Object> data;

        public RespuestasDashboard(HttpStatus status, String message, Map<String, Object> data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        // Getters y Setters
        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Map<String, Object> getData() {
            return data;
        }

        public void setData(Map<String, Object> data) {
            this.data = data;
        }
}

