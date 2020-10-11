package comp1110.ass2;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;


/**
 * @author LanXue
 * @date 2020-05-01 21:08
 */
public class GetPointsTest {

    public static final String NO_TILE_SEQUENCE = "";

    public static final String[] NO_POINT_TRACKS = {
            "aaaa02acba12bcbc11dacc13dbba72",
            "cccc03badb37bbbb04baac13cbaa12dacc14ddbc15baac73dbba71cdac72baac50cbaa40ccda16dacc25adbb11",
            "dada50dacc51bbad61dacc62cddb03baac04bcdd05acba14aaaa13aacb12dbcd11adad76cbaa66cdac56dada46badb65bcdd64accd54bbad55bbbb45cbcb35cddb36adad26cbcb16aaaa15"
    };

    public static final String[] NORMAL_STATION_TRACKS = {
            "ccda03cbcb04",
            "accd02ccda12cddb11cbaa13ddbc14ddbc24dbcd23accd03",
            "cdac06bcdd16baac17ddbc71ccda61dacc62dacc72adbb73",
            "dbcd17baac27aaaa37dbcd26dacc36bcbc47cccc02adad12accd22adad01accd32aaaa42cddb52dacc51cbaa50cbaa60",
    };


    public static final String[] CENTRE_STATION_TRACKS = {
            "aaaa04aaaa14aaaa24",
            "bcdd02cccc03dddd13baac04aaaa14aaaa24",
            "ccda50bcdd51acba52aaaa40badb41dddd31cbaa42dbba74cbcb75baac73badb63aacb65dbcd66cccc64ddbc54bbbb53bbad55dada61bcbc45"
    };

    public static final String COMPLETE_BOARD = "dada30adad31bbad21ddbc11bbbb10dacc12cbcb01cccc02bbad06cbcb16dacc15acba05adbb14acba04cddb40badb41cdac73accd76dddd66cbaa70aaaa77dbba26adbb20bcbc00aaaa07adad25cccc24dddd23cddb35acba36badb56baac75cbaa74aacb65aacb64cbaa72cbaa71dada61ccda62cbcb63baac53dbcd51ccda52ddbc60bcbc50dbcd67bcdd57baac47aaaa37dbba27aaaa03bcdd17accd13aacb22bcbc42aacb54baac55cdac45acba46bbbb32";

    @Test
    public void testNoTrack() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (int i = 1; i <= 32; i++) {
            assertEquals(0, Score.getPoints(i, NO_TILE_SEQUENCE));
        }
    }

    @Test
    public void testNoPointTrack() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (int i = 1; i <= 32; i++) {
            for (String s : NO_POINT_TRACKS) {
                assertEquals(0, Score.getPoints(i, s));
            }
        }
    }

    @Test
    public void testNormalStationTrack() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        assertEquals(2, Score.getPoints(4, NORMAL_STATION_TRACKS[0]));
        assertEquals(6, Score.getPoints(5, NORMAL_STATION_TRACKS[1]));
        assertEquals(4, Score.getPoints(20, NORMAL_STATION_TRACKS[2]));
        assertEquals(3, Score.getPoints(31, NORMAL_STATION_TRACKS[2]));
        assertEquals(11, Score.getPoints(6, NORMAL_STATION_TRACKS[3]));
        assertEquals(4, Score.getPoints(28, NORMAL_STATION_TRACKS[3]));
        assertEquals(7, Score.getPoints(30, NORMAL_STATION_TRACKS[3]));
        assertEquals(2, Score.getPoints(31, NORMAL_STATION_TRACKS[3]));
    }

    @Test
    public void testCentreStationTrack() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        assertEquals(6, Score.getPoints(4, CENTRE_STATION_TRACKS[0]));
        assertEquals(14, Score.getPoints(6, CENTRE_STATION_TRACKS[1]));
        assertEquals(10, Score.getPoints(13, CENTRE_STATION_TRACKS[2]));
        assertEquals(36, Score.getPoints(20, CENTRE_STATION_TRACKS[2]));
    }

    @Test
    public void testCompleteBoard() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        int[] points = {2, 2, 4, 10, 8, 12, 2, 2, 24, 2, 2, 3, 3, 3, 2, 4, 2, 4, 4, 10, 14, 8, 2, 2, 4, 2, 25, 6, 6, 3, 2, 16};
        for (int i = 1; i <= 32; i++) {
            assertEquals(points[i-1], Score.getPoints(i, COMPLETE_BOARD));
        }
    }
}
