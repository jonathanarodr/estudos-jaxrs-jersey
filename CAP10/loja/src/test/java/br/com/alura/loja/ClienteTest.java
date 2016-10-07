package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;

public class ClienteTest {
	
	private HttpServer server;
	private WebTarget target;
	private Client client;

	@Before
	public void before() {
	    this.server = Servidor.inicializaServidor();
	    
	    //cria config. do cliente
	    ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
	    
	    this.client = ClientBuilder.newClient(config);
		this.target = client.target("http://localhost:8080");
	}

    @After
    public void mataServidor() {
        this.server.stop();
    }
	
	@Test
	public void testaQueAConexaoComOServidorFunciona() {
		String conteudo = this.target.path("/carrinhos/1").request().get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

}
