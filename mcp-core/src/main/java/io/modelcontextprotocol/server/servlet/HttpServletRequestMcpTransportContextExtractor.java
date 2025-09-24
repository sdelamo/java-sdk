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
import java.util.function.Function;

/**
 * {@link McpTransportContextExtractor} implementation for {@link HttpServletRequest}.
 */
public class HttpServletRequestMcpTransportContextExtractor
		implements McpTransportContextExtractor<HttpServletRequest> {

	@Override
	public McpTransportContext extract(HttpServletRequest request) {
		return McpTransportContext.create(McpTransportContext.createMetadata(request::getHeader));
	}

}
