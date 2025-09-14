package cardoso.commerce.app.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 400) {
            return new DataNotFoundException("Product not found");
        }
        return new Exception("Exception while decoding " + methodKey);
    }
}
