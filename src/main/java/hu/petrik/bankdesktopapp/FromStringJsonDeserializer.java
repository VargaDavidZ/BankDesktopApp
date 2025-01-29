package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;


public class FromStringJsonDeserializer<T> extends StdDeserializer<T> implements ContextualDeserializer {




    public FromStringJsonDeserializer() {
        super(Object.class);
    }

    public FromStringJsonDeserializer(JavaType type) {
        super(type);
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();

        return ((ObjectMapper) p.getCodec()).readValue(value, _valueType);
    }


    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        return new FromStringJsonDeserializer<>(property.getType());
    }
}
