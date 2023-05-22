package fr.lpdql.ptut.blocklytrader.runtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RunTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String testJson = "{\"blocks\":{\"languageVersion\":0,\"blocks\":[{\"type\":\"controls_if\"," + "\"id" +
            "\":\"k38QBFRc~G9guEH,SoA_\",\"x\":0,\"y\":97," + "\"inputs\":{\"IF0\":{\"block\":{\"type" +
            "\":\"logic_compare\",\"id\":\"PdHKdY)" + "7Ub]C3_XKGju1\",\"fields\":{\"OP\":\"LT\"}," + "\"inputs" +
            "\":{\"A\":{\"block\":{\"type\":\"klines_variables\",\"id\":\"Z}C," + "{rjp$|+P1+aM3*mJ\"," + "\"fields" + "\":{\"DATA_TYPE\":\"close\"}}}," + "\"B\":{\"block\":{\"type\":\"math_number\"," + "\"id\":\"}M" + "`QNb3UFRbf3-PnC^$t\"," + "\"fields\":{\"NUM\":27500}}}}}}," + "\"DO0\":{\"block\":{\"type\":\"buy\"," + "\"id\":\"2/YZpGq-v06Dc/E(+%u1\",\"fields\":{\"UNIT\":\"$\"}," + "\"inputs\":{\"VALUE\":{\"block" + "\":{\"type\":\"math_number\"," + "\"id\":\"%#hcJM0DqeC/FY:;{kK\",\"fields\":{\"NUM\":10}}}}}}}}]}}";


    @BeforeEach
    void setUp() throws Exception {
        mockMvc.perform(get("/getdata/btc/usdt").param("startTime", "1684281600000").param("endTime", "1684713600000")
                .param("frequency", "1h").contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Parse Exception : Running test with bad 'Blockly Json'")
    public void shouldRaiseParseException() throws Exception {
        mockMvc.perform(get("/runtest/").param("blocklyJson", "Ceci est un Json défectueux").param("cryptoBalance", "0")
                        .param("deviseBalance", "1000").param("exchangeFees", "0.075").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Une exception de type ParseException s'est produite."));
    }

    @Test
    @DisplayName("Path Not Found Exception : Running test with empty 'Blockly Json'")
    public void shouldRaisePathNotFoundException() throws Exception {
        mockMvc.perform(
                        get("/runtest/").param("blocklyJson", "{}").param("cryptoBalance", "0").param("deviseBalance"
                                        , "1000")
                                .param("exchangeFees", "0.075").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Une exception de type PathNotFound s'est produite."));
    }

    @Test
    @DisplayName("Illegal Argument Exception : Running test with negative Crypto balance")
    public void shouldRaiseIllegalArgumentExceptionCryptoBalance() throws Exception {
        mockMvc.perform(get("/runtest/").param("blocklyJson", testJson).param("cryptoBalance", "-100")
                        .param("deviseBalance", "1000").param("exchangeFees", "0.075").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Une exception de type IllegalArgument s'est produite."));
    }

    @Test
    @DisplayName("Illegal Argument Exception : Running test with negative Devise Balance")
    public void shouldRaiseIllegalArgumentExceptionDeviseBalance() throws Exception {
        mockMvc.perform(get("/runtest/").param("blocklyJson", testJson).param("cryptoBalance", "0")
                        .param("deviseBalance", "-1000").param("exchangeFees", "0.075").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Une exception de type IllegalArgument s'est produite."));
    }

    @Test
    @DisplayName("Illegal Argument Exception : Running test with negative Exchange Rate")
    public void shouldRaiseIllegalArgumentExceptionExchangeRate() throws Exception {
        mockMvc.perform(get("/runtest/").param("blocklyJson", testJson).param("cryptoBalance", "0")
                        .param("deviseBalance", "1000").param("exchangeFees", "-0.075").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Une exception de type IllegalArgument s'est produite."));
    }

}