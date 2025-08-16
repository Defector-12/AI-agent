export type SseMessageHandler = (chunk: string) => void;
export type SseErrorHandler = (e: Event) => void;

export interface SseOptions {
  onMessage: SseMessageHandler;
  onError?: SseErrorHandler;
}

export function openSse(url: string, options: SseOptions) {
  const eventSource = new EventSource(url);
  eventSource.onmessage = (event) => {
    options.onMessage(event.data);
  };
  eventSource.onerror = (e) => {
    options.onError?.(e);
    eventSource.close();
  };
  return eventSource;
}

