package com.yardox.proxyupdate;

import com.yardox.proxyupdate.persistence.model.MyProxy;
import com.yardox.proxyupdate.persistence.service.IMyProxyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProxyupdateApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IMyProxyService myProxyService;

    @Test
    public void rootShouldReturnIndexHtml() throws Exception {
        ResultActions test = this.mockMvc.perform(get("/"));
        this.mockMvc.perform(get("/"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("forward:index.html"));
    }

    @Test
    public void showProxiesShouldReturnTableProxies() throws Exception {

        this.mockMvc.perform(get("/showProxies"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("List proxy")));
    }

    @Test
    public void dataSourceReturnData() throws Exception {
        List<MyProxy> myProxies = (List<MyProxy>) myProxyService.findAll();
        assertThat(myProxies).isNotEmpty();
    }

}
