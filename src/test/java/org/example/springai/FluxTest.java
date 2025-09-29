package org.example.springai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest
public class FluxTest {
    @Test
    public void Mono() {
        Mono<String> empty = Mono.just("1");
        Mono<Object> empty1 = Mono
                .empty();
        empty.subscribe(System.out::println);
        System.out.println("----------------");
        empty1.subscribe(System.out::println);
    }
    @Test
    public void Flux() {
        Flux<String> flux = Flux.just("1", "2", "3", "4", "5")
                .doOnComplete(() -> System.out.println("<UNK>"));
        Flux<Object> flux1 = Flux.empty().doOnComplete(() -> System.out.println("<UNK1>"));
        //只能为Integer类型的,从start开始,递增count个数
        Flux<Integer> flux2 = Flux.range(6, 5);

        List<String> list = List.of("1", "2", "3", "4", "5", "6");
        Flux.fromIterable(list).subscribe(System.out::println);
        System.out.println("Flux----------------");
        flux.subscribe(System.out::println);
        System.out.println("Flux1---------------");
        flux1.subscribe(System.out::println);
        System.out.println("Flux2---------------");
        flux2.subscribe(System.out::println);
        //转换 & 处理

        Flux<Integer> flux3 = Flux.range(1, 10)
                .map(n -> n * n)
                .filter(integer -> integer % 2 != 0)
                .take(3)
                .skip(1);
        System.out.println("Flux3---------------");
        flux3.subscribe(System.out::println);
        //合并&组合
        Flux<Integer> flux4 = Flux.range(11, 3)
                .concatWith(flux3)
                .mergeWith(flux3);

        Flux<Integer> flux5 = Flux.just(1, 2);
        Flux<Integer> flux6 = Flux.just(3, 4);

        Flux<Integer> zipped = Flux.zip(flux5, flux6, Integer::sum);
        zipped.subscribe(System.out::println);



        System.out.println("Flux4---------------");
        flux4.subscribe(System.out::println);

    }
}
