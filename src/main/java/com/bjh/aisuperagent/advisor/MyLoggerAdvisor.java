package com.bjh.aisuperagent.advisor;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;
import org.springframework.ai.chat.model.MessageAggregator;

/**
 * 自定义日志级别 Advisor
 * 打印 info 级别日志，只输出单词用户提示词和 AI 回复的文本
 *
 */
@Slf4j
// implements 关键字表示这个 MyLoggerAdvisor 类 实现（implements） 了 CallAroundAdvisor 和 StreamAroundAdvisor 这两个 接口（Interface）
public class MyLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

	@Override
	public String getName() {
		// this：表示当前对象的引用。在这里，this 指的是 MyLoggerAdvisor 类的当前实例。
		return this.getClass().getSimpleName();
	}

	@Override
	public int getOrder() {
		return 0;
	}

	// before 方法在 AI 请求发出前被调用，用于记录用户提示词。
	private AdvisedRequest before(AdvisedRequest request) {
		log.info("AI Request:{}", request.userText());
		return request;
	}

	private void observeAfter(AdvisedResponse advisedResponse) {
		log.info("AI Response:{}", advisedResponse.response().getResult().getOutput().getText());
	}

	@Override
	// 这是方法的 返回类型（Return Type）。它表明当这个方法执行完毕时，它会返回一个 AdvisedResponse 类型的对象。如果一个方法不需要返回任何值，它的返回类型就是 void。

	public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
		/**
		 * AdvisedRequest advisedRequest：这定义了第一个参数。它的 类型 是 AdvisedRequest，名称 是 advisedRequest。
		 * 这意味着调用 aroundCall 方法时，必须传入一个 AdvisedRequest 类型的对象，并且在这个方法内部，可以通过 advisedRequest 这个名字来引用它。
		 */

        advisedRequest = before(advisedRequest);

		AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);

		observeAfter(advisedResponse);

		return advisedResponse;
	}

	@Override
	public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {

		advisedRequest = before(advisedRequest);

		Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);

		return new MessageAggregator().aggregateAdvisedResponse(advisedResponses, this::observeAfter);
	}

}
