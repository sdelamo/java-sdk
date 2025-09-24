/*
 * Copyright 2025-2025 the original author or authors.
 */
package io.modelcontextprotocol.server.servlet;

import io.modelcontextprotocol.common.McpTransportContext;
import io.modelcontextprotocol.server.McpTransportContextExtractor;
import io.modelcontextprotocol.spec.ProtocolVersions;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * {@link McpTransportContextExtractor} implementation for {@link HttpServletRequest}.
 */
public class HttpServletRequestMcpTransportContextExtractor
		implements McpTransportContextExtractor<HttpServletRequest> {

	@Override
	public McpTransportContext extract(HttpServletRequest request) {
		return McpTransportContext.create(metadata(request));
	}

	/**
	 * @param request Servlet Request
	 * @return Extracts Map for MCP Transport Context
	 */
	protected Map<String, Object> metadata(HttpServletRequest request) {
		Map<String, Object> metadata = new HashMap<>(3);
		metadata.put(io.modelcontextprotocol.spec.HttpHeaders.PROTOCOL_VERSION,
				Optional.ofNullable(request.getHeader(io.modelcontextprotocol.spec.HttpHeaders.PROTOCOL_VERSION))
					.orElse(ProtocolVersions.MCP_2025_03_26));
		Optional.ofNullable(request.getHeader(io.modelcontextprotocol.spec.HttpHeaders.MCP_SESSION_ID))
			.ifPresent(v -> metadata.put(io.modelcontextprotocol.spec.HttpHeaders.MCP_SESSION_ID, v));
		Optional.ofNullable(request.getHeader(io.modelcontextprotocol.spec.HttpHeaders.LAST_EVENT_ID))
			.ifPresent(v -> metadata.put(io.modelcontextprotocol.spec.HttpHeaders.LAST_EVENT_ID, v));
		return metadata;
	}

}
