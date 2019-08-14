<h1>SuperUltraVinGen 3000</h1>
Maven project SuperUltraVinGen 3000 - REST-сервис разработанный с использованием SpringBoot, задача которого генерировать валидный автомобильный VIN-номер. В качестве хранилища данных был выбран Redis со структурой данных Set.
<h2>Конфигурация и развертывание сервиса</h2>
<ul>
  Для работы сервиса необходимо:
  <li>Удостовериться, что установлена Java версии 1.8.0 или выше;</li>
  <li>Развернуть <a href="https://github.com/microsoftarchive/redis" title="Redis GitHub">Redis</a>;</li>
  <li>Подложить нашу базу данных <a href="https://github.com/inspectorcat/aplanaHahaton/releases" title="Тут лежит Джарка, конфиг и базёнка :)">dump.rdb</a>  для Redis;</li>
  <li>В файле <a href="https://github.com/inspectorcat/aplanaHahaton/releases" title="Тут лежит Джарка, конфиг и базёнка :)">application.properties</a> изменить следующие строки:
    <ul>
      <li>redis.host: localhost // Адрес хоста, на котором развернут Redis</li>
      <li>redis.port: 6379 // Порт, на котором развернут Redis</li>
      <li>server.port: 8090 //Порт, на котором будет запущен сервис</li>
    </ul>
  </li>
  <li>Для создания *.jar-файла необходимо используя любую из сред разработки с помощью Maven выполнить команду Clean, затем команду Package, после чего *.jar-файл будет доступен в корне проекта .\target;</li>
  <li>Или можно скачать уже готовый файл <a href="https://github.com/inspectorcat/aplanaHahaton/releases" title="Тут лежит Джарка, конфиг и базёнка :)">туть</a>;</li>
  <li>Подложить в папку с *.jar-файлом конфиг;</li>
  <li>Для запуска сервиса необходимо в командной строке написать следующее:<br />
  java -jar путь_к_*.jar-файлу --spring.config.location=file:путь_к_application.properties</li>
  <li>Или запустить сервис из среды разработки. В этом случае следует отредактировать файл application.properties в папке с ресурсами</li>
</ul>
<h2>Проверка работоспособности сервиса</h2>
<p>Сервис развернут на linux-машине в сети Aplana и доступен по адресу 192.168.100.153:9999.
<br />Проверка работы сервиса осуществляется отправкой GET-запроса на данный адрес. В ответ приходит JSON вида:<br />{"vin":"2FMGK5BC8BA121033"}<br /></p>
<h2>Проверка валидности VIN-номера</h2>
<p>Из полученного JSON-объекта нас интересует значение <b>2FMGK5BC8BA121033</b>.<br />
Копируем его, и проверяем на <a href="https://www.autodna.ru/" title="Проверка VIN-номера">сайте.</a></p>
