package io.modelcontextprotocol.server.httpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import io.modelcontextprotocol.common.McpTransportContext;
import io.modelcontextprotocol.spec.HttpHeaders;
import io.modelcontextprotocol.spec.ProtocolVersions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HttpExchangeMcpTransportContextExtractorTests {

	@Test
	@DisplayName("extract() includes all provided headers in metadata")
	void extractIncludesAllHeaders() {
		Headers headers = new Headers();
		headers.add(HttpHeaders.PROTOCOL_VERSION, "2025-03-26");
		headers.add(HttpHeaders.MCP_SESSION_ID, "session-123");
		headers.add(HttpHeaders.LAST_EVENT_ID, "evt-9");

		HttpExchange exchange = mock(HttpExchange.class);
		when(exchange.getRequestHeaders()).thenReturn(headers);
		HttpExchangeMcpTransportContextExtractor extractor = new HttpExchangeMcpTransportContextExtractor();

		McpTransportContext ctx = extractor.extract(exchange);

		assertEquals("2025-03-26", ctx.get(HttpHeaders.PROTOCOL_VERSION));
		assertEquals("session-123", ctx.get(HttpHeaders.MCP_SESSION_ID));
		assertEquals("evt-9", ctx.get(HttpHeaders.LAST_EVENT_ID));

		verify(exchange, times(1)).getRequestHeaders();
		verifyNoMoreInteractions(exchange);
	}

	@Test
	@DisplayName("extract() defaults protocol version when header missing")
	void extractDefaultsProtocolVersion() {
		Headers headers = new Headers();
		HttpExchange exchange = mock(HttpExchange.class);
		when(exchange.getRequestHeaders()).thenReturn(headers);
		HttpExchangeMcpTransportContextExtractor extractor = new HttpExchangeMcpTransportContextExtractor();

		McpTransportContext ctx = extractor.extract(exchange);

		assertEquals(ProtocolVersions.MCP_2025_03_26, ctx.get(HttpHeaders.PROTOCOL_VERSION));

		verify(exchange, times(1)).getRequestHeaders();
		verifyNoMoreInteractions(exchange);
	}

	@Test
	@DisplayName("extract() omits optional headers when not present")
	void extractOmitsOptionalHeaders() {
		Headers headers = new Headers();
		headers.add(HttpHeaders.PROTOCOL_VERSION, ProtocolVersions.MCP_2025_03_26);
		HttpExchange exchange = mock(HttpExchange.class);
		when(exchange.getRequestHeaders()).thenReturn(headers);
		HttpExchangeMcpTransportContextExtractor extractor = new HttpExchangeMcpTransportContextExtractor();

		McpTransportContext ctx = extractor.extract(exchange);

		assertNull(ctx.get(HttpHeaders.MCP_SESSION_ID));
		assertNull(ctx.get(HttpHeaders.LAST_EVENT_ID));

		verify(exchange, times(1)).getRequestHeaders();
		verifyNoMoreInteractions(exchange);
	}

}