#HobGobblin

Набор кастомных классов для построения pipeline для Gobblin.

*Pipeline* в понятии Gobblin - цепочка преобразований над данными(сообщениями) 
и их схемами по пути от источника (Source) до их публикации (Publisher)

## Основные сущности Gobblin Task Pipeline

* *Extractor* - отвечает за извлечение данных из произвольного источника в рамках выделенного *Source* *WorkUnit*'а
* *Converter* - преобразование данных и схем их описывающих
* *QualityChecker* - фильтрация и выделение невалидных данных для последующего разбора
* *ForkOperator* - разделение потока данных для записи в различных формах или для разных потребителей
* *Writer* - сериализация и запись данных
* *Publisher* - формат подачи данных конечному потребителю

### Дополнительные сущности

* *Partitioner* - помогает *Publisher*'у распределить данные(сообщения) в зависимости от его схемы, предоставляя 
необходимые данные (например поля записи, отметку времени и т.п.)

Основная задача при сборке pipeline - аккуратно скомпоновать классы продюсеры и потребители
на основе их входных-выходных типов и схем
 
## Kafka-Byte-Pipeline

Встроенный в Gobblin pipeline, где в качестве схемы используется `String`, а сообщение идет в виде 
 `byte[]`. Структура его:
 
```
source.class=gobblin.source.extractor.extract.kafka.KafkaSimpleSource
-> <String, byte[]> ->
writer.builder.class=gobblin.writer.SimpleDataWriterBuilder

data.publisher.type=gobblin.publisher.BaseDataPublisher
```

Или с включенным партитиционированием по времени:
```
writer.partitioner.class=ru.rambler.hobgobblin.partitioner.ArrivalPartitioner
writer.partition.level=daily
writer.partition.pattern=YYYY-MM-dd

data.publisher.type=gobblin.publisher.TimePartitionedDataPublisher
data.publisher.appendExtractToFinalDir=false
```

Соответственно данные без особых преобразований кладутся в HDFS

## Kafka-TimeStamp-Pipeline

Реализованный на классах *HobGobblin* pipeline: 

```
source.class=gobblin.source.extractor.extract.kafka.KafkaSimpleSource
-> <String, byte[]> ->
converter.classes=ru.rambler.hobgobblin.converter.BegunJsonConverter
-> <String, TimestampRecord>> ->
writer.builder.class=ru.rambler.hobgobblin.writer.TimestampRecordWriterBuilder

writer.partitioner.class=ru.rambler.hobgobblin.partitioner.TimestampRecordPartitioner
writer.partition.level=daily
writer.partition.pattern=YYYY-MM-dd
data.publisher.type=gobblin.publisher.TimePartitionedDataPublisher
data.publisher.appendExtractToFinalDir=false
```