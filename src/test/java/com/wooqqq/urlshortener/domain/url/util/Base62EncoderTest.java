package com.wooqqq.urlshortener.domain.url.util;

import com.wooqqq.urlshortener.global.util.Base62Encoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Base62EncoderTest {

    @Test
    @DisplayName("인코딩 후 디코딩 시 원래 ID와 같다")
    void encodeAndDecode() {
        long id = 123456L;
        String shortKey = Base62Encoder.encode(id);
        assertThat(Base62Encoder.decode(shortKey)).isEqualTo(id);
    }

    @Test
    @DisplayName("encode 결과는 Base62 문자만 포함한다")
    void encodeResultContainsOnlyBase62Chars() {
        String shortKey = Base62Encoder.encode(999999L);
        assertThat(shortKey).matches("[0-9a-zA-Z]+");
    }

    @Test
    @DisplayName("작은 값과 큰 값 모두 정상 인코딩된다")
    void encodeVariousValues() {
        assertThat(Base62Encoder.encode(1L)).isNotEmpty();
        assertThat(Base62Encoder.encode(1_000_000L)).isNotEmpty();
    }

    @Test
    @DisplayName("동일한 ID는 항상 동일한 shortKey를 반환한다")
    void sameIdAlwaysReturnsSameShortKey() {
        long id = 42L;
        assertThat(Base62Encoder.encode(id)).isEqualTo(Base62Encoder.encode(id));
    }
}
