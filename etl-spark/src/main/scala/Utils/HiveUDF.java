package Utils;

import org.apache.hadoop.hive.ql.exec.UDF;

public class HiveUDF extends UDF {
    public String evaluate (final String s) {
        if (s == null) {
            return null;
        }
        return s.toLowerCase();
    }
}
