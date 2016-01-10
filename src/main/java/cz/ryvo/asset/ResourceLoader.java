package cz.ryvo.asset;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class ResourceLoader {

    /** The list of locations to be searched */
    private static ArrayList locations = new ArrayList();

    static {
        locations.add(new ClassPathLocation());
        locations.add(new FileSystemLocation(new File(".")));
    }

    public static InputStream getResourceAsStream(String resource) {
        InputStream in = null;

        for (int i=0;i<locations.size();i++) {
            ResourceLocation location = (ResourceLocation) locations.get(i);
            in = location.getResourceAsStream(resource);
            if (in != null) {
                break;
            }
        }

        if (in == null)
        {
            throw new RuntimeException("Resource not found: " + resource);
        }

        return new BufferedInputStream(in);
    }
}
