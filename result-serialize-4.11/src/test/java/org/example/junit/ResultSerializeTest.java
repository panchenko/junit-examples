package org.example.junit;

import com.google.common.io.BaseEncoding;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertEquals;

public class ResultSerializeTest {
    private static final String HEX = "ACED0005737200176F72672E6A756E69742E72756E6E65722E526573756C7400000000000000010200054A00086652756E54696D654A000A66537461727454696D654C000666436F756E7474002B4C6A6176612F7574696C2F636F6E63757272656E742F61746F6D69632F41746F6D6963496E74656765723B4C0009664661696C757265737400104C6A6176612F7574696C2F4C6973743B4C000C6649676E6F7265436F756E7471007E0001787000000000000000000000000000000000737200296A6176612E7574696C2E636F6E63757272656E742E61746F6D69632E41746F6D6963496E7465676572563F5ECC8C6C168A02000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000001737200266A6176612E7574696C2E436F6C6C656374696F6E732453796E6368726F6E697A65644C6973749463EFE38344107C0200014C00046C69737471007E00027872002C6A6176612E7574696C2E436F6C6C656374696F6E732453796E6368726F6E697A6564436F6C6C656374696F6E2A61F84D099C99B50300024C0001637400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C00056D757465787400124C6A6176612F6C616E672F4F626A6563743B7870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000007704000000007871007E000B7871007E000D7371007E000400000002";

    @Test
    public void serialize() throws Exception {
        Result result = new Result();
        RunListener listener = result.createListener();
        final Description description = Description.createTestDescription(ResultSerializeTest.class, "success");
        listener.testStarted(description);
        listener.testFinished(description);
        listener.testIgnored(Description.createTestDescription(ResultSerializeTest.class, "ignored1"));
        listener.testIgnored(Description.createTestDescription(ResultSerializeTest.class, "ignored2"));

        assertEquals(1, result.getRunCount());
        assertEquals(2, result.getIgnoreCount());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream stream = new ObjectOutputStream(baos)) {
            stream.writeObject(result);
        }
        assertEquals(HEX, BaseEncoding.base16().encode(baos.toByteArray()));
    }

    @Test
    public void deserialize() throws Exception {
        Result result;
        try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(BaseEncoding.base16().decode(HEX)))) {
            result = (Result) stream.readObject();
        }
        assertEquals(1, result.getRunCount());
        assertEquals(2, result.getIgnoreCount());
    }
}
