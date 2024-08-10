package dom.com.thesismonolitserver.convertors;

import org.springframework.core.convert.converter.Converter;

import java.util.Map;
import java.util.Optional;

public class GenericConverterType {
    private final Map<Class<?>, Converter<?, ?>> mapOfClassConverterAndItsImplementation;

    public GenericConverterType(Map<Class<?>, Converter<?, ?>> mapOfClassConverterAndItsImplementation) {
        this.mapOfClassConverterAndItsImplementation = mapOfClassConverterAndItsImplementation;
    }

    public Converter<?, ?> getConverter(Class<?> clazz){
        Optional<? extends Converter<?, ?>> iGenericConvertersTypeOptional = Optional.ofNullable(mapOfClassConverterAndItsImplementation.get(clazz));
        return  iGenericConvertersTypeOptional.orElseThrow(()-> new RuntimeException("We don't have such converter for class " + clazz.toString()));
    }
}
