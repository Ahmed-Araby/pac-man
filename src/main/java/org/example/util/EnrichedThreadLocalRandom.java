package org.example.util;

import java.util.concurrent.ThreadLocalRandom;

public class EnrichedThreadLocalRandom {

    public double nextPercentage() {
        return ThreadLocalRandom.current().nextInt(101);
    }
    public int nextIntStartInclEndIncl(int start, int end) {
        return ThreadLocalRandom.current().nextInt(end - start + 1) + start;
    }

    public int nextIntStartInclEndExcl(int start, int end) {
        return ThreadLocalRandom.current().nextInt(end - start) + start;
    }


    public int nextIntStartExclEndIncl(int start, int end) {
        return start + 1 + ThreadLocalRandom.current().nextInt(end - start);
    }

    public int nextIntStartExclEndExcl(int start, int end) {
        return start + 1 + ThreadLocalRandom.current().nextInt(end - start -1);
    }
}
