package comp1110.ass2;

import comp1110.ass2.tiles.TileInterface;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author LanXue
 * @date 2020-05-01 20:03
 */
public class ExceedMaxNumberTest {

    public static final String NO_TILE_SEQUENCE = "";
    public static final String[] VALID_FOUR_COPY_TILE_SEQUENCES = {
            "aacbaacbaacbaacb",
            "cbaacbaacbaacbaa",
            "baacbaacbaacbaac",
            "aaaaaaaaaaaaaaaa",
            "aacbaacbaacbaacbcbaacbaacbaacbaabaacbaacbaacbaacaaaaaaaaaaaaaaaa"
    };
    public static final String[] VALID_THREE_COPY_TILE_SEQUENCES = {
            "cbcbcbcbcbcb",
            "bcbcbcbcbcbc",
            "cbcbcbcbcbcbbcbcbcbcbcbc"
    };
    public static final String[] VALID_TWO_COPY_TILE_SEQUENCES = {
            "cccccccc",
            "adbbadbbadbb",
            "dddddadadddddadabbadbbadbcddbcddcdacddbccdacddbc"

    };
    public static final String[] VALID_MIXED_SEQUENCES = {
            "aaaaddbcaacbadadbcddcdaccdaccbaaacba",
            "bcbcbbadcdacbadbbbadacbaddddbcbcadbbbadbaacbcbaabaacaaaaaccdccccbcbcbbbb",
            "baacdbcddaccaccdcdaccbcbbbbbdacccbaaddddddbcbbbbaaaadddddadaadbbdbbaddbcccdacddbcbaaacbabadb",
    };
    public static final String[] INVALID_MIXED_SEQUENCES = {
            "ddddbcbcccccbbbbddddadaddddd",
            "aacbbbadddbcdbcdaacbaaaaaacbbcbccbcbddddbbadbbad",
            "cbcbbaacbbbbaacbbcbcaacbdaccdbbaadbbcccccbcbbcbccbcbbaacdbcdddddbaacccccbbbbcccc"
    };

    @Test
    public void testNoneTileString() {
        assertEquals(false, TileInterface.exceedMaxNumber(NO_TILE_SEQUENCE));
    }

    @Test
    public void testValidFourCopyTileStrings() {
        for (String s : VALID_FOUR_COPY_TILE_SEQUENCES) {
            boolean expected = false;
            boolean out = TileInterface.exceedMaxNumber(s);
            assertTrue("Expected to be " + expected +" but got " + out, expected == out);
        }
    }

    @Test
    public void testValidThreeCopyTilesStrings() {
        for (String s : VALID_THREE_COPY_TILE_SEQUENCES) {
            assertEquals(false, TileInterface.exceedMaxNumber(s));
        }
    }

    @Test
    public void testValidTwoCopyTilesStrings() {
        for (String s : VALID_THREE_COPY_TILE_SEQUENCES) {
            assertEquals(false, TileInterface.exceedMaxNumber(s));
        }
    }

    @Test
    public void testValidMixedStrings() {
        for (String s : VALID_MIXED_SEQUENCES) {
            assertEquals(false, TileInterface.exceedMaxNumber(s));
        }
    }

    @Test
    public void testInvalidMixedStrings() {
        for (String s : INVALID_MIXED_SEQUENCES) {
            assertEquals(true, TileInterface.exceedMaxNumber(s));
        }
    }
}
