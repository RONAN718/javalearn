package io.kimmking.rpcfx.api;

import lombok.Data;

@Data
public class RpcfxRequest<T> {
  private String serviceClass;
  private String method;
  private Object[] params;
  private Class<T> serviceClazz;
}
