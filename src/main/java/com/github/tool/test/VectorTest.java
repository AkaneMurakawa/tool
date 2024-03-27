package com.github.tool.test;

public class VectorTest {

    public static void main(String[] args) {

    }

    // 选择合适的分批大小
    // static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;
    //
    // static void vectorComputation(float[] a, float[] b, float[] c) {
    //     int i = 0;
    //     int upperBound = SPECIES.loopBound(a.length);
    //     for (; i < upperBound; i += SPECIES.length()) {
    //         FloatVector va = FloatVector.fromArray(SPECIES, a, i);
    //         FloatVector vb = FloatVector.fromArray(SPECIES, b, i);
    //         var vc = va.mul(va).add(vb.mul(vb)).neg();
    //         vc.intoArray(c, i);
    //     }
    // }
}
