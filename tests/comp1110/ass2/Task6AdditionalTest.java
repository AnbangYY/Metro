package comp1110.ass2;

import comp1110.ass2.tiles.TileInterface;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class Task6AdditionalTest {
    public static final String[] VALID_RANDOM_PLACEMENT_SEQUENCES = {
            "cccc20dddd21accd22baac23accd24dbcd25cdac26",
            "adad03cccc04aaaa14dddd24accd25bcbc35baac45cddb46bcdd47dbcd37",
            "dacc20accd21aacb31dada41aacb42cbaa40bcdd51accd61cccc71adad52cddb62adad72cdac50bbbb60cbaa70cbaa30aacb10adbb01acba00aaaa11dbba12acba02cdac22ccda32badb03dbba13cddb23aaaa53adbb63bcdd54bcbc73cbaa64dddd24baac14bbad04bbbb05aaaa15baac25bcbc35aacb45aaaa55dbcd65baac75cbcb74baac06dbcd16bcbc26dacc36badb46dada56bbad66cccc76ccda07ddbc17dddd27cbcb37acba47acba57cbcb67ddbc77"
    };

    public static final String[] VALID_EDGE_SEQUENCES ={
            "bbbb01cbcb02bbad03dbba10dbcd72cdac30dada40cbaa50",
            "cccc10cdac20ccda30aacb40bcdd02bcbc03bcdd05bcbc04bbad27baac06bcbc37cccc74bbad73bbbb72ddbc75aacb71aaaa47dada57dacc67acba77aaaa70ddbc60cbcb50badb17aacb07cbcb01cbaa76"
    };

    public static final String[] VALID_CORNER_SEQUENCES = {
            "aacb07cbcb70",
            "aacb07cbaa70",
            "baac00"
    };

    public static final String[] INVALID_EDGED_SEQUENCES = {
            "bcdd06bbad17dacc03",
            "ccda04cbcb05acba06dacc47accd74dada71",
            "ddbc30accd37aaaa27adad76acba57badb60aaaa50cbaa20aaaa17dbcd10",
            "bbad27cbaa20cddb30adad37cddb40cbaa50ddbc60bbad47aaaa57cbaa67"
    };

    public static final String[] INVALID_CORNERED_SEQUENCES = {
            "bcdd02aaaa03badb04baac13bcbc14dddd23dada27dada37aacb47cccc57cbcb00",
            "cbcb20bbbb30cddb40cddb50bbad31dada32cbcb22adad23aacb24cbaa25dada35badb36badb46cbaa37adad56aacb57cbcb60cccc77",
            "badb20aaaa30badb40baac31adbb32cbaa22dddd23dbcd24acba25bbad26baac17bcbc07",
            "bcbc10dbba20cccc30cbaa40baac50dbba60ddbc70"
    };

    public static final String[] INVALID_MIXED_SEQUENCES = {
            "bcdd02aaaa03badb04baac13bcbc14dddd23dada27dada37dacc03aacb47cccc57cbcb00",
            "ddbc30accd37aaaa27adad76acba57badb60aaaa50cbaa20bcbc07aaaa17dbcd10",
            "bbad27cbaa20cddb30adad37cddb40cbaa50ddbc60ddbc70bbad47aaaa57cbaa67"
    };

    @Test
    public void test_valid_sequence(){
        for(String s : VALID_RANDOM_PLACEMENT_SEQUENCES){
           assertTrue("Expected to be " + true +" but got " + false, Metro.isPlacementSequenceValid(s));
    }
    }

    @Test
    public void test_valid_edge_sequence(){
        for(String s : VALID_EDGE_SEQUENCES){
            assertTrue("Expected to be " + true +" but got " + false, Metro.isPlacementSequenceValid(s));
        }
    }

    @Test
    public void test_corner_sequence(){
        for(String s : VALID_CORNER_SEQUENCES){
            assertTrue("Expected to be " + true +" but got " + false, Metro.isPlacementSequenceValid(s));
        }
    }

    @Test
    public void test_invalid_edged_sequence(){
        for(String s : INVALID_EDGED_SEQUENCES){
            assertEquals(false , Metro.isPlacementSequenceValid(s));
        }
    }

    @Test
    public void test_invalid_cornered_sequence(){
        for(String s : INVALID_CORNERED_SEQUENCES){
            assertEquals(false , Metro.isPlacementSequenceValid(s));
        }
    }

    @Test
    public void test_invalid_mixed_sequence(){
        for(String s : INVALID_MIXED_SEQUENCES){
            assertEquals(false , Metro.isPlacementSequenceValid(s));
        }
    }
}
