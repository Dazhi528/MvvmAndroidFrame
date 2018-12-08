package com.dazhi.libroot.rx;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**=======================================
 * 作者：WangZezhi  (2018/4/11  15:52)
 * 功能：restrofit 自定义 StringConverterFactory
 * 描述：
 *=======================================*/
public class StringConverterFactory extends Converter.Factory {
    private static final StringConverterFactory INSTANCE = new StringConverterFactory();
    private final StringConverter INSTANCE_CONVERTER=new StringConverter();

    private StringConverterFactory(){
    }
    public static StringConverterFactory create() {
        return INSTANCE;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return INSTANCE_CONVERTER;
        }
        //其它类型我们不处理，返回null就行
        return null;
    }


    /**=======================================
     * 作者：WangZezhi  (2018/4/11  16:01)
     * 功能：内部类，实现Converter
     * 描述：
     *=======================================*/
    private class StringConverter implements Converter<ResponseBody, String> {
        @Override
        public String convert(ResponseBody responseBody) throws IOException {
            return responseBody.string();
        }
    }


}
