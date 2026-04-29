// Русский язык (Russian Language Pack)
export default {
  // Общие
  common: {
    search: 'Поиск',
    reset: 'Сброс',
    submit: 'Отправить',
    cancel: 'Отмена',
    confirm: 'Подтвердить',
    delete: 'Удалить',
    edit: 'Редактировать',
    add: 'Добавить',
    view: 'Просмотр',
    export: 'Экспорт',
    import: 'Импорт',
    download: 'Скачать',
    upload: 'Загрузить',
    operate: 'Действие',
    status: 'Статус',
    enable: 'Включить',
    disable: 'Отключить',
    yes: 'Да',
    no: 'Нет',
    save: 'Сохранить',
    close: 'Закрыть',
    back: 'Назад',
    refresh: 'Обновить',
    loading: 'Загрузка...',
    success: 'Операция успешна',
    error: 'Операция не удалась',
    warning: 'Предупреждение',
    info: 'Информация',
    pleaseSelect: 'Пожалуйста, выберите',
    pleaseInput: 'Пожалуйста, введите',
    startDate: 'Дата начала',
    endDate: 'Дата окончания',
    startTime: 'Время начала',
    endTime: 'Время окончания',
    createTime: 'Время создания',
    updateTime: 'Время обновления',
    remark: 'Примечание',
    action: 'Действие',
    more: 'Еще',
    all: 'Все',
    selectAll: 'Выбрать все',
    empty: 'Нет данных',
    total: 'Всего {total} записей',
    pageSize: 'записей/страница',
    goTo: 'Перейти',
    page: 'страница',
    index: '№',
    operator: 'Оператор',
    creator: 'Создатель',
    publishTime: 'Время публикации',
    systemBuiltIn: 'Встроенный в систему',
    loginInfo: 'Информация о входе',
    operInfo: 'Инфо об операции',
    normal: 'Нормально',
    failed: 'Ошибка',
    standalone: 'Отдельный',
    cluster: 'Кластерный',
    refreshCache: 'Обновить кэш',
    selectDate: 'Выбрать дату',
    systemTitle: 'Система управления энергией',
    // Right Toolbar
    showSearch: 'Показать поиск',
    hideSearch: 'Скрыть поиск',
    showHideColumns: 'Показать/скрыть столбцы',
    showHideTitle: 'Показать/скрыть',
    show: 'Показать',
    hide: 'Скрыть',
    // Cron Builder
    crontab: {
      second: 'Секунда',
      minute: 'Минута',
      hour: 'Час',
      day: 'День',
      month: 'Месяц',
      week: 'Неделя',
      year: 'Год',
      timeExpression: 'Временное выражение',
      cronExpression: 'Cron выражение'
    },
    moreMenu: 'Ещё меню',
    // File Upload
    upload: {
      selectFile: 'Выбрать файл',
      pleaseUpload: 'Пожалуйста загрузите',
      sizeLimit: 'Размер не более',
      formatLimit: 'Формат',
      fileSuffix: 'файл',
      uploading: 'Загрузка файла, пожалуйста подождите...',
      uploadFailed: 'Ошибка загрузки, повторите попытку',
      fileTypeError: 'Неверный формат файла',
      fileSizeError: 'Размер файла не может превышать'
    }
  },

  // Вход
  login: {
    title: 'Система управления энергией Чжуронг',
    username: 'Имя пользователя',
    password: 'Пароль',
    captcha: 'Код проверки',
    remember: 'Запомнить меня',
    login: 'Войти',
    logging: 'Вход...',
    usernameRequired: 'Пожалуйста, введите имя пользователя',
    passwordRequired: 'Пожалуйста, введите пароль',
    captchaRequired: 'Пожалуйста, введите код проверки',
    loginSuccess: 'Вход выполнен успешно',
    loginError: 'Ошибка входа',
    logout: 'Выйти',
    logoutConfirm: 'Вы уверены, что хотите выйти?'
  },

  // Меню
  menu: {
    system: {
      title: 'Управление системой',
      user: 'Управление пользователями',
      role: 'Управление ролями',
      menu: 'Управление меню',
      dept: 'Управление отделами',
      post: 'Управление должностями',
      dict: 'Управление словарями',
      config: 'Управление конфигурацией',
      notice: 'Объявления',
      log: {
        title: 'Управление журналами',
        operlog: 'Журнал операций',
        logininfor: 'Журнал входа'
      }
    },
    monitor: {
      title: 'Мониторинг системы',
      online: 'Пользователи онлайн',
      job: 'Запланированные задачи',
      druid: 'Мониторинг данных',
      server: 'Мониторинг сервера',
      cache: 'Мониторинг кэша',
      redis: 'Мониторинг Redis'
    },
    tool: {
      title: 'Инструменты системы',
      build: 'Конструктор форм',
      gen: 'Генератор кода',
      swagger: 'API документация',
      icon: 'Библиотека иконок'
    },
    energy: {
      title: 'Управление энергией',
      overview: 'Обзор энергии',
      analysis: 'Анализ энергии',
      report: 'Отчет об энергии',
      quota: 'Квота энергии',
      plan: 'План энергии'
    },
    equipment: {
      title: 'Управление оборудованием',
      list: 'Список оборудования',
      type: 'Тип оборудования',
      status: 'Статус оборудования',
      maintenance: 'Обслуживание',
      repair: 'Управление ремонтом'
    },
    alarm: {
      title: 'Управление тревогами',
      current: 'Тревоги в реальном времени',
      history: 'История тревог',
      rule: 'Правила тревог',
      level: {
        general: 'Обычный',
        urgent: 'Срочный',
        serious: 'Серьезный'
      }
    },
    camera: {
      title: 'Видеонаблюдение',
      realtime: 'Видео в реальном времени',
      playback: 'Воспроизведение видео',
      config: 'Настройка камеры'
    },
    charging: {
      title: 'Зарядная станция',
      station: 'Зарядная станция',
      pile: 'Зарядная колонка',
      order: 'Заказ на зарядку',
      price: 'Ценовая стратегия'
    },
    inspection: {
      title: 'Управление инспекциями',
      plan: 'План инспекции',
      task: 'Задача инспекции',
      record: 'Запись инспекции',
      point: 'Точка инспекции'
    },
    itemized: {
      title: 'Детальный учет',
      topology: 'Управление топологией',
      analysis: 'Детальный анализ'
    },
    dataQuery: {
      title: 'Запрос данных',
      realtime: 'Данные в реальном времени',
      history: 'Исторические данные',
      statistics: 'Статистика'
    },
    profile: {
      title: 'Профиль',
      info: 'Личная информация',
      password: 'Изменить пароль'
    },
    control: {
      title: 'Централизованное управление',
      control: 'Централизованное управление'
    },
    metering: {
      title: 'Управление измерением',
      metering: 'Управление измерением'
    },
    carbon: {
      title: 'Управление углеродными активами',
      analysis: 'Анализ углерода'
    },
    energyAnalysis: {
      title: 'Анализ энергопотребления',
      energyOverview: 'Обзор энергопотребления',
      energyFlow: 'Энергетический поток',
      energyTrend: 'Тренд энергопотребления',
      yoyAnalysis: 'Анализ YoY',
      monAnalysis: 'Анализ MoM',
      lossAnalysis: 'Анализ потерь',
      expenseBoard: 'Панель затрат',
      expenseReport: 'Отчет затрат'
    },
    digitaltwin: {
      title: 'Цифровой двойник',
      digitaltwin: 'Цифровой двойник'
    },
    inventory: {
      title: 'Управление инвентарем',
      attachment: 'Управление вложениями',
      purveyor: 'Управление поставщиками'
    },
    managementSystem: {
      title: 'Система управления',
      prePlan: 'Управление планами',
      process: 'Управление процессами',
      regulation: 'Управление правилами',
      standard: 'Управление стандартами'
    },
    maintenance: {
      title: 'Управление обслуживанием',
      duty: 'Управление дежурством',
      inspectionPlan: 'План инспекции',
      inspectionRecord: 'Записи инспекции',
      repairOrder: 'Заказ на ремонт',
      schedule: 'Управление расписанием',
      exampleReport: 'Пример отчета',
      myInspection: 'Моя инспекция',
      myRepairOrder: 'Мой заказ',
      itemTopology: 'Топология оборудования'
    },
    quota: {
      title: 'Управление квотами',
      analysis: 'Анализ квот',
      config: 'Конфигурация квот',
      monitor: 'Мониторинг квот'
    },
    report: {
      title: 'Управление отчетами',
      generate: 'Генерация отчета',
      template: 'Шаблон отчета'
    },
    newenergy: {
      title: 'Новая энергия',
      energyStorage: 'Хранилище энергии',
      microGrid: 'Микросеть',
      pvStation: 'ФЭС станция',
      storageBattery: 'Аккумуляторная батарея'
    },
    analysisReport: {
      title: 'Отчет об анализе',
      analysisReport: 'Отчет об анализе'
    },
    dataQuery: {
      title: 'Запрос данных',
      electricParams: 'Электрические параметры'
    },
    dataBoard: {
      title: 'Панель данных',
      dataBoard: 'Панель данных'
    },
    dashboard: {
      title: 'Панель',
      dashboard: 'Панель'
    },
    visualization3D: {
      title: '3D Визуализация',
      visualization3D: '3D Визуализация'
    },
    business: {
      title: 'Управление бизнесом',
      business: 'Управление бизнесом'
    },
    item: {
      title: 'Управление предметами',
      item: 'Управление предметами'
    },
    operation: {
      title: 'Операционное управление',
      operation: 'Операционное управление'
    },
    alarmManage: {
      title: 'Управление тревогами',
      realtime: 'Тревоги в реальном времени',
      history: 'Исторические тревоги',
      rule: 'Правила тревог',
      analysis: 'Анализ тревог'
    },
    newEnergyManage: {
      title: 'Управление новой энергией',
      energyStorage: 'Накопление энергии',
      microGrid: 'Микросеть',
      pvStation: 'ФЭС станция',
      storageBattery: 'Аккумуляторная батарея'
    },
    managementSystem2: {
      title: 'Система управления',
      prePlan: 'Предварительный план',
      process: 'Процесс',
      regulation: 'Регламент',
      standard: 'Стандарт'
    },
    carbonAnalysis: {
      title: 'Анализ углерода',
      analysis: 'Анализ выбросов углерода'
    },
    powerParam: {
      title: 'Запрос параметров электроэнергии',
      query: 'Запрос параметров'
    },
    videoConfig: {
      title: 'Конфигурация видео',
      config: 'Конфигурация камеры'
    },
    realTimeView: {
      title: 'Просмотр в реальном времени',
      view: 'Видео в реальном времени'
    },
    orgManage: {
      title: 'Управление организацией',
      dept: 'Управление отделом'
    },
    paramSet: {
      title: 'Настройка параметров',
      config: 'Системные параметры'
    },
    fileManage: {
      title: 'Управление файлами',
      files: 'Список файлов'
    },
    billingType: {
      title: 'Тип биллинга',
      type: 'Тип биллинга'
    },
    adminMonitor: {
      title: 'Монитор администратора',
      monitor: 'Системный монитор'
    },
    jobScheduler: {
      title: 'Планировщик задач',
      job: 'Запланированные задачи'
    },
    cacheList: {
      title: 'Список кэша',
      list: 'Монитор кэша'
    },
    usageDetect: {
      title: 'Обнаружение использования',
      detect: 'Монитор использования'
    },
    energyConsumeTrend: {
      title: 'Тренд энергии',
      trend: 'Тренд энергии'
    },
    energyStatus: {
      title: 'Статус энергии',
      status: 'Статус энергии'
    },
    usageMonitor: {
      title: 'Монитор использования',
      monitor: 'Монитор использования'
    },
    energyOverview: {
      title: 'Обзор энергии',
      overview: 'Обзор энергии'
    },
    energyFlow: {
      title: 'Поток энергии',
      flow: 'Поток энергии'
    },
    itemOverview: {
      title: 'Обзор элементов',
      overview: 'Обзор элементов'
    },
    deviceInfo: {
      title: 'Информация об устройстве',
      info: 'Список устройств'
    },
    gateway: {
      title: 'Управление шлюзом',
      manage: 'Список шлюзов'
    },
    projectTopology: {
      title: 'Топология проекта',
      topology: 'Управление топологией'
    },
    chargingManage: {
      title: 'Управление зарядкой',
      station: 'Зарядная станция',
      pile: 'Зарядная стойка',
      order: 'Заказ на зарядку',
      price: 'Стратегия биллинга'
    },
    hazardous: {
      title: 'Опасные материалы',
      goods: 'Информация об опасных материалах',
      info: 'Управление информацией об опасных материалах',
      stockIn: 'Запись о поступлении опасных материалов',
      stockOut: 'Запись о выбытии опасных материалов',
      inventory: 'Управление запасами',
      warning: 'Предупреждение о запасах'
    },
    patrol: {
      title: 'Управление патрулированием',
      point: 'Точки патрулирования',
      route: 'Маршруты патрулирования',
      plan: 'Планы патрулирования',
      record: 'Записи патрулирования',
      alarm: 'Тревоги патрулирования',
      task: 'Задачи патрулирования'
    },
    itemManage: {
      title: 'Управление предметами',
      info: 'Информация о предметах',
      inventory: 'Запасы предметов',
      stockIn: 'Поступление предметов',
      stockOut: 'Выбытие предметов'
    },
    chargingOperation: {
      title: 'Эксплуатация зарядки',
      home: 'Главная зарядка',
      order: {
        title: 'Управление заказами',
        realtime: 'Заказы в реальном времени',
        history: 'Исторические заказы',
        abnormal: 'Аномальные заказы',
        occupied: 'Занятые заказы'
      },
      merchant: {
        title: 'Управление торговцами',
        platform: 'Платформенные торговцы',
        interconnect: 'Подключенные торговцы'
      },
      station: {
        title: 'Управление станциями',
        direct: 'Прямые станции',
        interconnect: 'Подключенные станции'
      },
      pile: {
        title: 'Управление стойками',
        direct: 'Прямые стойки',
        interconnect: 'Подключенные стойки'
      },
      brand: 'Бренд и модель',
      price: 'Цена и сборы',
      strategy: 'Стратегия цен на зарядку'
    },
    pvManage: {
      title: 'Управление ФЭС',
      standard: 'Стандарты работы',
      billing: 'Схема биллинга'
    }
  },

  // Навигация
  navbar: {
    home: 'Главная',
    profile: 'Профиль',
    settings: 'Настройки',
    fullscreen: 'Полный экран',
    exitFullscreen: 'Выйти из полного экрана',
    size: 'Размер макета',
    sizeDefault: 'По умолчанию',
    sizeMedium: 'Средний',
    sizeSmall: 'Маленький',
    sizeMini: 'Мини',
    theme: 'Настройки темы',
    themeLight: 'Светлая тема',
    themeDark: 'Темная тема',
    language: 'Сменить язык',
    search: 'Поиск меню'
  },

  // Вкладки
  tagsView: {
    refreshPage: 'Обновить страницу',
    closeCurrent: 'Закрыть текущую',
    closeOthers: 'Закрыть другие',
    closeAll: 'Закрыть все',
    closeLeft: 'Закрыть слева',
    closeRight: 'Закрыть справа'
  },

  // Настройки
  settings: {
    title: 'Конфигурация системы',
    platformName: 'Название платформы',
    platformNamePlaceholder: 'Введите название платформы',
    platformLogo: 'Логотип платформы',
    platformLogoPlaceholder: 'Введите логотип платформы',
    topNav: 'Включить TopNav',
    tagsView: 'Включить Tags-Views',
    fixedHeader: 'Фиксированный заголовок',
    sidebarLogo: 'Логотип боковой панели'
  },

  // Placeholder
  placeholder: {
    username: 'Пожалуйста, введите имя пользователя',
    password: 'Пожалуйста, введите пароль',
    captcha: 'Пожалуйста, введите код проверки',
    userName: 'Пожалуйста, введите имя пользователя',
    nickName: 'Пожалуйста, введите никнейм',
    phonenumber: 'Пожалуйста, введите номер телефона',
    email: 'Пожалуйста, введите email',
    deptName: 'Пожалуйста, введите название отдела',
    roleName: 'Пожалуйста, введите название роли',
    postName: 'Пожалуйста, введите название должности',
    dictType: 'Пожалуйста, введите название словаря',
    configName: 'Пожалуйста, введите название параметра',
    menuName: 'Пожалуйста, введите название меню',
    noticeTitle: 'Пожалуйста, введите заголовок объявления',
    search: 'Пожалуйста, введите ключевое слово',
    startDate: 'Дата начала',
    endDate: 'Дата окончания',
    selectDept: 'Пожалуйста, выберите отдел',
    selectPost: 'Пожалуйста, выберите должность',
    selectRole: 'Пожалуйста, выберите роль',
    selectGender: 'Пожалуйста, выберите пол',
    selectStatus: 'Пожалуйста, выберите статус',
    select: 'Пожалуйста, выберите',
    input: 'Пожалуйста, введите'
  },

  // Форма проверки
  validation: {
    required: '{field} обязательно для заполнения',
    email: 'Пожалуйста, введите правильный адрес электронной почты',
    phone: 'Пожалуйста, введите правильный номер телефона',
    number: 'Пожалуйста, введите число',
    minLength: 'Длина не может быть меньше {min} символов',
    maxLength: 'Длина не может превышать {max} символов'
  },

  // Button
  button: {
    search: 'Поиск',
    reset: 'Сброс',
    add: 'Добавить',
    edit: 'Редактировать',
    delete: 'Удалить',
    import: 'Импорт',
    export: 'Экспорт',
    download: 'Скачать',
    upload: 'Загрузить',
    submit: 'Отправить',
    cancel: 'Отмена',
    close: 'Закрыть',
    save: 'Сохранить',
    more: 'Еще',
    refresh: 'Обновить',
    expand: 'Развернуть',
    collapse: 'Свернуть',
    back: 'Назад',
    view: 'Просмотр',
    operate: 'Действие',
    confirm: 'Подтвердить',
    select: 'Выбрать',
    clear: 'Очистить',
    generate: 'Сгенерировать',
    preview: 'Предпросмотр',
    copy: 'Копировать',
    move: 'Переместить',
    enable: 'Включить',
    disable: 'Отключить',
    start: 'Запустить',
    stop: 'Остановить',
    run: 'Выполнить',
    detail: 'Подробности',
    log: 'Журнал',
    auth: 'Назначить',
    resetPwd: 'Сбросить пароль',
    authRole: 'Назначить роль',
    authUser: 'Назначить пользователя'
  },

  // Table Column
  table: {
    userId: 'ID пользователя',
    userName: 'Имя пользователя',
    nickName: 'Никнейм',
    deptName: 'Отдел',
    phonenumber: 'Телефон',
    status: 'Статус',
    createTime: 'Время создания',
    updateTime: 'Время обновления',
    operate: 'Действие',
    roleId: 'ID роли',
    roleName: 'Название роли',
    roleKey: 'Ключ роли',
    roleSort: 'Сортировка роли',
    deptId: 'ID отдела',
    parentDept: 'Родительский отдел',
    orderNum: 'Порядок',
    leader: 'Руководитель',
    postId: 'ID должности',
    postName: 'Название должности',
    postCode: 'Код должности',
    postSort: 'Сортировка должности',
    dictId: 'ID словаря',
    dictName: 'Название словаря',
    dictType: 'Тип словаря',
    configId: 'ID параметра',
    configName: 'Название параметра',
    configKey: 'Ключ параметра',
    configValue: 'Значение параметра',
    menuId: 'ID меню',
    menuName: 'Название меню',
    icon: 'Иконка',
    path: 'Путь',
    component: 'Компонент',
    perms: 'Разрешение',
    visible: 'Видимость',
    isFrame: 'Внешняя ссылка',
    isCache: 'Кэш',
    noticeId: 'ID объявления',
    noticeTitle: 'Заголовок объявления',
    noticeType: 'Тип объявления',
    noticeContent: 'Содержание',
    loginTime: 'Время входа',
    ipaddr: 'IP адрес',
    loginLocation: 'Местоположение',
    browser: 'Браузер',
    os: 'ОС',
    operId: 'ID журнала',
    title: 'Модуль',
    businessType: 'Тип',
    method: 'Метод',
    requestMethod: 'Запрос',
    operName: 'Оператор',
    operUrl: 'URL',
    operIp: 'IP',
    operParam: 'Параметры',
    jsonResult: 'Результат',
    errorMsg: 'Ошибка',
    operTime: 'Время',
    costTime: 'Время(мс)'
  },

  // Form Label
  form: {
    userName: 'Имя пользователя',
    nickName: 'Никнейм',
    dept: 'Отдел',
    phonenumber: 'Телефон',
    email: 'Email',
    password: 'Пароль',
    gender: 'Пол',
    status: 'Статус',
    post: 'Должность',
    role: 'Роль',
    remark: 'Примечание',
    roleName: 'Название роли',
    roleKey: 'Ключ роли',
    roleSort: 'Сортировка роли',
    dataScope: 'Область данных',
    menuPermissions: 'Разрешения меню',
    deptName: 'Название отдела',
    parentDept: 'Родительский отдел',
    showOrder: 'Порядок отображения',
    leader: 'Руководитель',
    contactPhone: 'Контактный телефон',
    postName: 'Название должности',
    postCode: 'Код должности',
    postSort: 'Сортировка должности',
    dictName: 'Название словаря',
    dictType: 'Тип словаря',
    dictLabel: 'Метка',
    dictValue: 'Значение',
    dictSort: 'Сортировка',
    isDefault: 'По умолчанию',
    listClass: 'Стиль',
    configName: 'Название параметра',
    configKey: 'Ключ параметра',
    configValue: 'Значение параметра',
    menuName: 'Название меню',
    parentMenu: 'Родительское меню',
    menuType: 'Тип меню',
    menuIcon: 'Иконка меню',
    menuSort: 'Сортировка меню',
    isFrame: 'Внешняя ссылка',
    isCache: 'Кэширование',
    visible: 'Видимость',
    routePath: 'Путь маршрута',
    componentPath: 'Путь компонента',
    perms: 'Разрешение',
    noticeTitle: 'Заголовок объявления',
    noticeType: 'Тип объявления',
    status: 'Статус',
    noticeContent: 'Содержание',
    newPassword: 'Новый пароль'
  },

  // Message
  message: {
    addSuccess: 'Успешно добавлено',
    editSuccess: 'Успешно изменено',
    deleteSuccess: 'Успешно удалено',
    saveSuccess: 'Успешно сохранено',
    submitSuccess: 'Успешно отправлено',
    importSuccess: 'Успешно импортировано',
    exportSuccess: 'Успешно экспортировано',
    uploadSuccess: 'Успешно загружено',
    downloadSuccess: 'Успешно скачано',
    copySuccess: 'Успешно скопировано',
    operationSuccess: 'Операция успешна',
    addFailed: 'Ошибка добавления',
    editFailed: 'Ошибка изменения',
    deleteFailed: 'Ошибка удаления',
    saveFailed: 'Ошибка сохранения',
    submitFailed: 'Ошибка отправки',
    importFailed: 'Ошибка импорта',
    uploadFailed: 'Ошибка загрузки',
    operationFailed: 'Операция не удалась',
    confirmDelete: 'Вы уверены, что хотите удалить',
    confirmClear: 'Вы уверены, что хотите очистить',
    confirmLogout: 'Вы уверены, что хотите выйти',
    confirmExport: 'Подтвердить экспорт',
    selectOne: 'Пожалуйста, выберите одну запись',
    selectAtLeastOne: 'Пожалуйста, выберите хотя бы одну запись',
    selectRecord: 'Пожалуйста, выберите запись для операции',
    inputRequired: 'Пожалуйста, введите',
    selectRequired: 'Пожалуйста, выберите',
    deleteConfirm: 'После удаления восстановление невозможно, подтвердить?',
    enableSuccess: 'Успешно включено',
    disableSuccess: 'Успешно отключено',
    resetPwdSuccess: 'Пароль успешно сброшен, новый пароль: ',
    noData: 'Нет данных',
    loading: 'Загрузка...',
    clearingCache: 'Очистка кеша настроек и обновление, подождите...',
    systemError: 'Системная ошибка',
    networkError: 'Ошибка сети',
    timeout: 'Время ожидания истекло',
    invalidFileType: 'Неверный тип файла',
    fileTooLarge: 'Файл слишком большой'
  },

  // Confirm
  confirm: {
    title: 'Системное сообщение',
    confirmButtonText: 'Подтвердить',
    cancelButtonText: 'Отмена',
    delete: 'Вы уверены, что хотите удалить выбранные записи?',
    clear: 'Вы уверены, что хотите очистить все данные?',
    logout: 'Вы уверены, что хотите выйти?',
    export: 'Подтвердить экспорт данных?',
    import: 'Подтвердить импорт данных?',
    enable: 'Подтвердить включение этой записи?',
    disable: 'Подтвердить отключение этой записи?',
    resetPwd: 'Пожалуйста, введите новый пароль для "{username}"',
    authRole: 'Подтвердить назначение ролей?',
    authUser: 'Подтвердить назначение пользователей?',
    runTask: 'Подтвердить немедленное выполнение этой задачи?',
    clean: 'Подтвердить очистку кэша?',
    forceLogout: 'Подтвердить принудительный выход этого пользователя?'
  },

  // Validation Rules
  rules: {
    userNameRequired: 'Имя пользователя обязательно',
    userNameLength: 'Длина имени пользователя должна быть от 2 до 20',
    nickNameRequired: 'Никнейм обязателен',
    passwordRequired: 'Пароль обязателен',
    passwordLength: 'Длина пароля должна быть от 5 до 20',
    emailInvalid: 'Пожалуйста, введите правильный email',
    phoneInvalid: 'Пожалуйста, введите правильный номер телефона',
    deptRequired: 'Отдел обязателен',
    roleNameRequired: 'Название роли обязательно',
    roleKeyRequired: 'Ключ роли обязателен',
    roleSortRequired: 'Сортировка роли обязательна',
    deptNameRequired: 'Название отдела обязательно',
    postNameRequired: 'Название должности обязательно',
    postCodeRequired: 'Код должности обязателен',
    dictNameRequired: 'Название словаря обязательно',
    dictTypeRequired: 'Тип словаря обязателен',
    configNameRequired: 'Название параметра обязательно',
    configKeyRequired: 'Ключ параметра обязателен',
    menuNameRequired: 'Название меню обязательно',
    pathRequired: 'Путь маршрута обязателен',
    noticeTitleRequired: 'Заголовок объявления обязателен',
    noticeContentRequired: 'Содержание объявления обязательно'
  },

  // System Module
  system: {
    user: {
      title: 'Управление пользователями',
      importTitle: 'Импорт пользователей',
      importTip: 'Перетащите файл сюда или ',
      importClick: 'нажмите для загрузки',
      importFormat: 'Разрешены только файлы xls, xlsx',
      updateSupport: 'Обновить существующие данные пользователей',
      downloadTemplate: 'Скачать шаблон',
      importResult: 'Результат импорта',
      resetPwdTitle: 'Сообщение',
      resetPwdConfirm: 'Подтвердить',
      resetPwdCancel: 'Отмена',
      pwdLengthError: 'Длина пароля должна быть от 5 до 20'
    },
    role: {
      title: 'Управление ролями',
      authUserTitle: 'Назначить пользователей',
      selectUser: 'Выбрать пользователя',
      dataScope1: 'Все разрешения на данные',
      dataScope2: 'Пользовательские разрешения на данные',
      dataScope3: 'Разрешения на данные отдела',
      dataScope4: 'Разрешения на данные отдела и ниже',
      dataScope5: 'Только собственные разрешения на данные'
    },
    dept: {
      title: 'Управление отделами',
      expandAll: 'Развернуть/Свернуть',
      selectParent: 'Выбрать родительский отдел'
    },
    post: {
      title: 'Управление должностями'
    },
    dict: {
      title: 'Управление словарями',
      dataTitle: 'Данные словаря',
      addData: 'Добавить данные словаря',
      editData: 'Изменить данные словаря'
    },
    config: {
      title: 'Управление параметрами'
    },
    menu: {
      title: 'Управление меню',
      expandAll: 'Развернуть/Свернуть',
      selectIcon: 'Выбрать иконку',
      directory: 'Каталог',
      menu: 'Меню',
      button: 'Кнопка',
      parentMenu: 'Главная категория'
    },
    notice: {
      title: 'Объявления',
      addTitle: 'Добавить объявление',
      editTitle: 'Изменить объявление'
    },
    log: {
      operlog: {
        title: 'Журнал операций',
        detailTitle: 'Детали журнала операций',
        operId: 'ID операции',
        operModule: 'Модуль',
        operType: 'Тип',
        operDesc: 'Описание'
      },
      logininfor: {
        title: 'Журнал входа',
        unlock: 'Разблокировать',
        unlockSuccess: 'Успешно разблокировано'
      }
    }
  },

  // Monitor Module
  monitor: {
    online: {
      title: 'Пользователи онлайн',
      forceLogout: 'Принудительный выход',
      forceLogoutConfirm: 'Вы уверены, что хотите принудительно выйти этого пользователя?',
      forceLogoutSuccess: 'Принудительный выход успешен',
      sessionId: 'ID сессии',
      loginName: 'Имя входа',
      deptName: 'Отдел',
      host: 'Хост',
      loginTime: 'Время входа'
    },
    job: {
      title: 'Запланированные задачи',
      addTitle: 'Добавить задачу',
      editTitle: 'Изменить задачу',
      cronExpression: 'Выражение Cron',
      jobName: 'Название задачи',
      jobGroup: 'Группа задач',
      invokeTarget: 'Целевой объект вызова',
      misfirePolicy: 'Политика сбоев',
      concurrent: 'Параллельно',
      status: 'Статус',
      runOnce: 'Выполнить один раз',
      log: 'Журнал',
      logTitle: 'Журнал задач',
      logDetail: 'Детали журнала',
      jobLogId: 'ID журнала',
      jobMessage: 'Сообщение',
      isConcurrent: 'Параллельное выполнение',
      runOnceConfirm: 'Подтвердить немедленное выполнение этой задачи?',
      runSuccess: 'Выполнение успешно'
    },
    server: {
      title: 'Мониторинг сервера',
      cpu: 'CPU',
      memory: 'Память',
      disk: 'Диск',
      serverInfo: 'Информация о сервере',
      jvmInfo: 'Информация о JVM',
      diskInfo: 'Состояние диска',
      cpuUsage: 'Использование CPU',
      memoryUsage: 'Использование памяти',
      runTime: 'Время работы',
      startTime: 'Время запуска'
    },
    cache: {
      title: 'Мониторинг кэша',
      cacheName: 'Имя кэша',
      cacheKey: 'Ключ кэша',
      cacheValue: 'Значение кэша',
      clear: 'Очистить кэш',
      clearSuccess: 'Успешно очищено',
      list: 'Список кэша',
      keyList: 'Список ключей'
    }
  },

  // Dashboard
  dashboard: {
    title: 'Главная',
    projectOverview: 'Обзор проекта',
    deviceStatus: 'Состояние устройства',
    alarmInfo: 'Информация о тревогах',
    energyStatistic: 'Статистика энергии за сегодня',
    energyTrend: 'Тренд энергии за сегодня',
    carbonEmissions: 'Выбросы углерода за сегодня',
    projectTotal: 'Всего проектов',
    meterTotal: 'Всего счетчиков',
    alarmTotal: 'Записей тревог',
    normalDevice: 'Нормальное устройство',
    alarmDevice: 'Устройство с тревогой',
    offlineDevice: 'Оффлайн устройство',
    proportion: 'Доля',
    electricity: 'Электричество(кВт·ч)',
    comprehensiveEnergy: 'Энергия(кгце)',
    carbonEmission: 'Углерод(кг)',
    totalEnergy: 'Общая энергия',
    water: 'Вода',
    time: 'Время',
    more: 'Еще>>',
    chargingUser: 'Пользователи зарядки',
    chargingAmount: 'Сумма зарядки',
    chargingEnergy: 'Энергия зарядки',
    chargingCount: 'Количество зарядок',
    totalUser: 'Всего пользователей',
    totalAmount: 'Общая сумма',
    totalEnergyKwh: 'Общая энергия',
    totalCount: 'Общее количество',
    monthOverMonth: 'Месяц к месяцу',
    yearOverYear: 'Год к году',
    unitUser: 'пользователей',
    unitYuan: 'CNY',
    unitKwh: 'кВт·ч',
    unitCount: 'раз',
    chargingTerminalStatus: 'Статус терминала',
    chargingOrderTrend: 'Тренд заказов',
    last7DaysCount: 'Количество за 7 дней',
    last7DaysDuration: 'Длительность за 7 дней',
    last7DaysEnergy: 'Энергия за 7 дней',
    last7DaysAmount: 'Сумма за 7 дней',
    duration: 'Длительность',
    energy: 'Энергия',
    amount: 'Сумма',
    thisMonth: 'Этот месяц',
    thisYear: 'Этот год',
    day: 'День',
    month: 'Месяц'
  },

  // Energy Management Module
  energyModule: {
    title: 'Управление энергией',
    // Batch Management
    batch: {
      title: 'Управление партиями',
      batchId: 'ID партии',
      productName: 'Название продукта',
      batchNumber: 'Номер партии',
      productionDate: 'Дата производства',
      productionLine: 'Производственная линия',
      energyConsumption: 'Потребление энергии',
      unit: 'Единица',
      status: 'Статус',
      createTime: 'Время создания',
      remark: 'Примечание',
      placeholder: {
        searchBatch: 'Введите номер партии/название продукта',
        selectDate: 'Выберите дату производства'
      }
    },
    // Benchmark Management
    benchmark: {
      title: 'Управление эталонами',
      standardId: 'ID стандарта',
      standardName: 'Название стандарта',
      energyType: 'Тип энергии',
      benchmarkValue: 'Эталонное значение',
      unit: 'Единица',
      industry: 'Отрасль',
      region: 'Регион',
      effectiveDate: 'Дата вступления',
      status: 'Статус',
      remark: 'Примечание',
      placeholder: {
        searchStandard: 'Введите название стандарта',
        selectType: 'Выберите тип энергии'
      }
    },
    // Energy Balance Management
    balance: {
      title: 'Управление энергетическим балансом',
      balanceId: 'ID баланса',
      totalSupply: 'Общее снабжение',
      totalConsumption: 'Общее потребление',
      balanceAmount: 'Количество баланса',
      balanceRate: 'Коэффициент баланса',
      balanceDate: 'Дата баланса',
      calculate: 'Рассчитать энергетический баланс'
    },
    // Energy Quality Management
    quality: {
      title: 'Управление качеством энергии',
      qualityId: 'ID записи',
      qualityIndex: 'Показатель качества',
      standardValue: 'Стандартное значение',
      actualValue: 'Фактическое значение',
      deviation: 'Отклонение',
      detectionTime: 'Время обнаружения'
    },
    // Energy Analysis
    analysis: {
      title: 'Анализ энергии',
      trend: 'Тренд энергии',
      comparison: 'Сравнение энергии',
      ranking: 'Рейтинг энергии',
      report: 'Отчет анализа'
    }
  },

  // Charging Station Module
  chargingModule: {
    title: 'Управление зарядными станциями',
    // Station Management
    station: {
      title: 'Управление станциями',
      stationId: 'ID станции',
      stationName: 'Название станции',
      stationCode: 'Код станции',
      stationType: 'Тип станции',
      price: 'Цена станции',
      merchant: 'Торговец',
      merchantName: 'Название торговца',
      address: 'Адрес станции',
      status: 'Статус станции',
      longitude: 'Долгота',
      latitude: 'Широта',
      openTime: 'Время работы',
      parkFee: 'Плата за парковку',
      serviceFee: 'Плата за услугу',
      contactName: 'Контактное лицо',
      contactPhone: 'Телефон',
      createTime: 'Время создания',
      updateTime: 'Время обновления',
      remark: 'Примечание',
      placeholder: {
        searchStation: 'Введите название станции',
        searchMerchant: 'Введите название торговца',
        selectType: 'Выберите тип станции',
        selectStatus: 'Выберите статус станции',
        inputAddress: 'Введите адрес станции'
      }
    },
    // Charging Pile Management
    pile: {
      title: 'Управление зарядными колонками',
      pileId: 'ID колонки',
      pileName: 'Название колонки',
      pileCode: 'Код колонки',
      pileType: 'Тип колонки',
      pileModel: 'Модель колонки',
      station: 'Станция',
      stationName: 'Название станции',
      status: 'Статус колонки',
      power: 'Мощность',
      voltage: 'Напряжение',
      current: 'Ток',
      connectorType: 'Тип разъема',
      connectorCount: 'Количество разъемов',
      gunNumber: 'Номер пистолета',
      gunStatus: 'Статус пистолета',
      manufacturer: 'Производитель',
      productionDate: 'Дата производства',
      installationDate: 'Дата установки',
      lastMaintainTime: 'Последнее обслуживание',
      createTime: 'Время создания',
      remark: 'Примечание',
      encoding: 'Код терминала',
      name: 'Название терминала',
      merchantName: 'Название продавца',
      workStatus: 'Рабочий статус',
      batchEnable: 'Массовое включение',
      batchDisable: 'Массовое отключение',
      selectBrandFirst: 'Сначала выберите бренд',
      addTitle: 'Добавить зарядную колонку',
      editTitle: 'Редактировать зарядную колонку',
      deleteConfirm: 'Вы уверены, что хотите удалить зарядную колонку с ID "{ids}"?',
      batchEnableConfirm: 'Вы уверены, что хотите массово включить выбранные терминалы?',
      batchDisableConfirm: 'Вы уверены, что хотите массово отключить выбранные терминалы?',
      batchEnableSuccess: 'Массовое включение успешно',
      batchDisableSuccess: 'Массовое отключение успешно',
      statusConfirm: 'Вы уверены, что хотите {text} эту зарядную колонку?',
      placeholder: {
        searchPile: 'Введите название/код колонки',
        selectStation: 'Выберите станцию',
        selectType: 'Выберите тип колонки',
        selectStatus: 'Выберите статус колонки'
      }
    },
    // Brand Management
    brand: {
      title: 'Управление брендами',
      id: 'ID бренда',
      brandName: 'Название бренда',
      status: 'Статус бренда',
      addTitle: 'Добавить информацию о бренде',
      editTitle: 'Редактировать информацию о бренде',
      deleteConfirm: 'Вы уверены, что хотите удалить информацию о бренде с ID "{ids}"?',
      statusConfirm: 'Вы уверены, что хотите {text} этот бренд?',
      // Model Management
      modelId: 'ID модели',
      modelName: 'Название модели',
      modelStatus: 'Статус модели',
      addModelTitle: 'Добавить информацию о модели',
      editModelTitle: 'Редактировать информацию о модели',
      deleteModelConfirm: 'Вы уверены, что хотите удалить информацию о модели с ID "{ids}"?',
      modelStatusConfirm: 'Вы уверены, что хотите {text} эту модель?'
    },
    // Merchant Management
    merchant: {
      title: 'Управление продавцами',
      name: 'Название продавца',
      type: 'Тип продавца',
      contact: 'Контакт',
      address: 'Адрес',
      status: 'Статус продавца',
      managePile: 'Управление колонками',
      addTitle: 'Добавить информацию о продавце',
      editTitle: 'Редактировать информацию о продавце',
      deleteConfirm: 'Вы уверены, что хотите удалить информацию о продавце с ID "{ids}"?',
      statusConfirm: 'Вы уверены, что хотите {text} этого продавца?'
    },
    // Charging Order
    order: {
      title: 'Заказы на зарядку',
      orderNo: 'Номер заказа',
      userName: 'Имя пользователя',
      phone: 'Телефон',
      merchantName: 'Название продавца',
      stationName: 'Название станции',
      pileName: 'Название колонки',
      carNo: 'Номерной знак',
      carVin: 'VIN-код',
      chargeMethod: 'Способ зарядки',
      orderSource: 'Источник заказа',
      settleType: 'Тип расчета',
      orderStatus: 'Статус заказа',
      createTime: 'Время создания',
      startTime: 'Время начала',
      endTime: 'Время окончания',
      settleTime: 'Время расчета',
      settlePrice: 'Сумма расчета',
      paidPrice: 'Оплаченная сумма',
      discountAmt: 'Сумма скидки',
      elecAmt: 'Плата за электроэнергию',
      serveAmt: 'Плата за обслуживание',
      chargeDuration: 'Длительность зарядки',
      energy: 'Общая энергия',
      settleBalance: 'Баланс счета',
      payType: 'Способ оплаты',
      abnoCause: 'Причина аномалии',
      detail: 'Детали',
      process: 'Обработать',
      addTitle: 'Добавить информацию о заказе',
      editTitle: 'Редактировать информацию о заказе',
      deleteConfirm: 'Вы уверены, что хотите удалить информацию о заказе с ID "{ids}"?'
    },
      title: 'Заказы на зарядку',
      orderId: 'ID заказа',
      orderNo: 'Номер заказа',
      userId: 'ID пользователя',
      userName: 'Имя пользователя',
      userPhone: 'Телефон',
      stationName: 'Станция',
      pileName: 'Зарядная колонка',
      gunNumber: 'Номер пистолета',
      startTime: 'Время начала',
      endTime: 'Время окончания',
      chargeDuration: 'Длительность',
      chargeAmount: 'Заряжено',
      chargePower: 'Мощность',
      totalAmount: 'Общая сумма',
      electricityFee: 'Плата за электричество',
      serviceFee: 'Плата за услугу',
      discountAmount: 'Скидка',
      payAmount: 'Сумма к оплате',
      payStatus: 'Статус оплаты',
      payTime: 'Время оплаты',
      payMethod: 'Способ оплаты',
      orderStatus: 'Статус заказа',
      plateNumber: 'Номерной знак',
      vinCode: 'VIN код',
      socStart: 'Начальный SOC',
      socEnd: 'Конечный SOC',
      startMeterReading: 'Начальные показания',
      endMeterReading: 'Конечные показания',
      createTime: 'Время создания',
      placeholder: {
        searchOrder: 'Введите номер заказа',
        searchUser: 'Введите имя/телефон',
        selectStation: 'Выберите станцию',
        selectStatus: 'Выберите статус заказа',
        selectPayStatus: 'Выберите статус оплаты'
      }
    },
    // Pricing Strategy
    price: {
      title: 'Ценовая стратегия',
      strategyId: 'ID стратегии',
      strategyName: 'Название стратегии',
      strategyType: 'Тип ценообразования',
      timeType: 'Тип времени',
      startTime: 'Время начала',
      endTime: 'Время окончания',
      electricityPrice: 'Цена электричества',
      servicePrice: 'Цена услуги',
      parkPrice: 'Цена парковки',
      maxPrice: 'Макс. цена',
      minPrice: 'Мин. цена',
      minDuration: 'Мин. длительность',
      maxDuration: 'Макс. длительность',
      effectiveDate: 'Дата вступления',
      expiryDate: 'Срок действия',
      applicableStations: 'Применимые станции',
      status: 'Статус',
      remark: 'Примечание',
      placeholder: {
        searchStrategy: 'Введите название стратегии',
        selectType: 'Выберите тип ценообразования',
        selectStation: 'Выберите станцию'
      }
    },
    // Merchant Management
    merchant: {
      title: 'Управление торговцами',
      merchantId: 'ID торговца',
      merchantName: 'Название торговца',
      merchantCode: 'Код торговца',
      contactPerson: 'Контактное лицо',
      contactPhone: 'Телефон',
      email: 'Email',
      address: 'Адрес',
      businessLicense: 'Лицензия',
      accountName: 'Название счета',
      accountBank: 'Банк',
      accountNo: 'Номер счета',
      settlementCycle: 'Цикл расчета',
      commissionRate: 'Комиссия',
      status: 'Статус',
      createTime: 'Время создания',
      remark: 'Примечание',
      placeholder: {
        searchMerchant: 'Введите название торговца'
      }
    },
    // Brand Management
    brand: {
      title: 'Управление брендами',
      brandId: 'ID бренда',
      brandName: 'Название бренда',
      brandCode: 'Код бренда',
      brandLogo: 'Логотип',
      manufacturer: 'Производитель',
      country: 'Страна',
      website: 'Сайт',
      sort: 'Сортировка',
      status: 'Статус',
      createTime: 'Время создания',
      remark: 'Примечание',
      placeholder: {
        searchBrand: 'Введите название бренда'
      }
    },
    // Occupancy Order
    occupancyOrder: {
      title: 'Заказ на занятие',
      occupancyNo: 'Номер заказа на занятие',
      orderNo: 'Номер заказа на зарядку',
      settleStatus: 'Статус расчета',
      orderStatus: 'Статус заказа',
      duration: 'Длительность занятия',
      fee: 'Плата за занятие',
      isFee: 'Создана ли плата',
      payTime: 'Время оплаты',
      endReason: 'Причина завершения',
      orderInfo: 'Связанный заказ',
      userName: 'Имя пользователя',
      phone: 'Телефон',
      merchantName: 'Название продавца',
      stationName: 'Название станции',
      pileName: 'Название колонки',
      yes: 'Да',
      no: 'Нет',
      addTitle: 'Добавить заказ на занятие',
      editTitle: 'Редактировать заказ на занятие',
      deleteConfirm: 'Вы уверены, что хотите удалить заказ на занятие с ID "{ids}"?',
      validate: {
        idRequired: 'ID заказа на занятие обязательно',
        occupancyNoRequired: 'Номер заказа на занятие обязателен',
        orderNoRequired: 'Номер заказа на зарядку обязателен',
        durationRequired: 'Длительность занятия обязательна',
        feeRequired: 'Плата за занятие обязательна',
        isFeeRequired: 'Необходимо указать создана ли плата',
        payTimeRequired: 'Время оплаты обязательно',
        settleStatusRequired: 'Статус расчета обязателен',
        orderStatusRequired: 'Статус заказа обязателен',
        endReasonRequired: 'Причина завершения обязательна'
      }
    },
    // Order Detail
    orderDetail: {
      title: 'Детали заказа',
      orderInfo: 'Информация о заказе',
      pileInfo: 'Информация о колонке',
      terminalCode: 'Код терминала',
      terminalName: 'Название терминала',
      brand: 'Бренд',
      model: 'Модель',
      station: 'Станция',
      merchant: 'Продавец',
      contactPhone: 'Контактный телефон',
      stationAddress: 'Адрес станции',
      demandVoltage: 'Требуемое/фактическое напряжение',
      demandCurrent: 'Требуемый/фактический ток',
      soc: 'SOC',
      batteryTemp: 'Температура батареи',
      currentUnit: 'Ток/A',
      socUnit: 'SOC/%',
      tempUnit: 'Температура/℃',
      voltageUnit: 'Напряжение/V',
      yuan: 'CNY',
      kwh: 'кВт·ч',
      hour: 'ч'
    },

  // Inspection Management Module
  inspectionModule: {
    title: 'Управление инспекциями',
    // Inspection Task
    task: {
      title: 'Задача инспекции',
      taskId: 'ID задачи',
      taskName: 'Название задачи',
      taskCode: 'Код задачи',
      planId: 'План',
      planName: 'План инспекции',
      pathId: 'Маршрут',
      pathName: 'Название маршрута',
      userId: 'Инспектор',
      userName: 'Инспектор',
      inspector: 'Инспектор',
      inspectDate: 'Дата инспекции',
      startTime: 'Время начала',
      endTime: 'Время окончания',
      deadline: 'Крайний срок',
      taskStatus: 'Статус задачи',
      pointCount: 'Количество точек',
      checkedCount: 'Проверено',
      abnormalCount: 'Аномалии',
      completionRate: 'Процент выполнения',
      checkResult: 'Результат проверки',
      remark: 'Примечание',
      placeholder: {
        searchTask: 'Введите название задачи',
        selectPlan: 'Выберите план',
        selectPath: 'Выберите маршрут',
        selectUser: 'Выберите инспектора',
        selectStatus: 'Выберите статус'
      }
    },
    // Inspection Plan
    plan: {
      title: 'План инспекции',
      planId: 'ID плана',
      planName: 'Название плана',
      planCode: 'Код плана',
      frequency: 'Частота',
      cycleType: 'Тип цикла',
      cycleDay: 'Дни цикла',
      startTime: 'Время начала',
      endTime: 'Время окончания',
      pathId: 'Маршрут',
      pathName: 'Название маршрута',
      executorType: 'Тип исполнителя',
      executor: 'Исполнитель',
      remindTime: 'Время напоминания',
      status: 'Статус',
      createTime: 'Время создания',
      remark: 'Примечание',
      placeholder: {
        searchPlan: 'Введите название плана',
        selectPath: 'Выберите маршрут',
        selectFrequency: 'Выберите частоту',
        selectStatus: 'Выберите статус'
      }
    },
    // Inspection Path
    path: {
      title: 'Маршрут инспекции',
      pathId: 'ID маршрута',
      pathName: 'Название маршрута',
      pathCode: 'Код маршрута',
      pointCount: 'Количество точек',
      estimatedTime: 'Расчетное время',
      estimatedDistance: 'Расчетное расстояние',
      region: 'Регион',
      status: 'Статус',
      createTime: 'Время создания',
      remark: 'Примечание',
      placeholder: {
        searchPath: 'Введите название маршрута',
        selectRegion: 'Выберите регион'
      }
    },
    // Inspection Point
    point: {
      title: 'Точка инспекции',
      pointId: 'ID точки',
      pointName: 'Название точки',
      pointCode: 'Код точки',
      pointType: 'Тип точки',
      location: 'Местоположение',
      region: 'Регион',
      longitude: 'Долгота',
      latitude: 'Широта',
      rfidCode: 'RFID код',
      qrCode: 'QR код',
      checkItem: 'Пункт проверки',
      checkStandard: 'Стандарт проверки',
      normalValue: 'Нормальное значение',
      deviceId: 'Устройство',
      deviceName: 'Название устройства',
      photoRequired: 'Требуется фото',
      remarkRequired: 'Требуется примечание',
      sort: 'Сортировка',
      status: 'Статус',
      createTime: 'Время создания',
      remark: 'Примечание',
      placeholder: {
        searchPoint: 'Введите название точки',
        selectType: 'Выберите тип точки',
        selectRegion: 'Выберите регион'
      }
    },
    // Inspection Record
    record: {
      title: 'Запись инспекции',
      recordId: 'ID записи',
      taskId: 'Задача',
      taskName: 'Название задачи',
      pointId: 'Точка',
      pointName: 'Название точки',
      checkTime: 'Время проверки',
      checkResult: 'Результат проверки',
      checkValue: 'Значение',
      isNormal: 'Нормально',
      normal: 'Нормально',
      abnormal: 'Аномалия',
      abnormalDesc: 'Описание аномалии',
      abnormalType: 'Тип аномалии',
      abnormalLevel: 'Уровень аномалии',
      handler: 'Обработчик',
      handleTime: 'Время обработки',
      handleResult: 'Результат обработки',
      handleStatus: 'Статус обработки',
      photo: 'Фото',
      signPhoto: 'Фото подписи',
      remark: 'Примечание',
      placeholder: {
        searchRecord: 'Введите название точки',
        selectResult: 'Выберите результат',
        selectStatus: 'Выберите статус'
      }
    },
    // Inspection Alarm
    alarm: {
      title: 'Тревога инспекции',
      alarmId: 'ID тревоги',
      alarmTitle: 'Заголовок',
      alarmType: 'Тип тревоги',
      alarmLevel: 'Уровень',
      alarmContent: 'Содержание',
      alarmTime: 'Время тревоги',
      pointId: 'Точка',
      pointName: 'Название точки',
      pathName: 'Название маршрута',
      taskName: 'Название задачи',
      handler: 'Обработчик',
      handleTime: 'Время обработки',
      handleResult: 'Результат',
      handleStatus: 'Статус обработки',
      status: 'Статус',
      createTime: 'Время создания',
      placeholder: {
        searchAlarm: 'Введите заголовок',
        selectType: 'Выберите тип тревоги',
        selectLevel: 'Выберите уровень',
        selectStatus: 'Выберите статус'
      }
    }
  },

  // Hazardous Materials Management Module
  hazardousModule: {
    title: 'Управление опасными материалами',
    // Hazardous Goods Info
    goods: {
      title: 'Информация об опасных веществах',
      goodsId: 'ID товара',
      goodsName: 'Название',
      goodsCode: 'Код',
      goodsType: 'Тип',
      goodsCategory: 'Категория',
      status: 'Статус',
      specification: 'Спецификация',
      casNo: 'CAS номер',
      unCode: 'UN код',
      formula: 'Формула',
      molecularWeight: 'Молекулярный вес',
      appearance: 'Внешний вид',
      density: 'Плотность',
      meltingPoint: 'Температура плавления',
      boilingPoint: 'Температура кипения',
      flashPoint: 'Температура вспышки',
      storageTemp: 'Температура хранения',
      hazardClass: 'Класс опасности',
      hazardProperties: 'Свойства опасности',
      storageRequirements: 'Требования хранения',
      handlingPrecautions: 'Меры предосторожности',
      emergencyMeasures: 'Аварийные меры',
      fireFightingMeasures: 'Пожаротушение',
      firstAidMeasures: 'Первая помощь',
      supplier: 'Поставщик',
      manufacturer: 'Производитель',
      storageLocation: 'Место хранения',
      safetyStock: 'Безопасный запас',
      maxStock: 'Макс. запас',
      unit: 'Единица',
      validityPeriod: 'Срок годности',
      msdsFile: 'MSDS файл',
      labelFile: 'Файл этикетки',
      warningSign: 'Предупреждающий знак',
      createTime: 'Время создания',
      remark: 'Примечание',
      placeholder: {
        searchGoods: 'Введите название/CAS',
        selectType: 'Выберите тип',
        selectCategory: 'Выберите категорию',
        selectStatus: 'Выберите статус'
      }
    },
    // Inventory Management
    inventory: {
      title: 'Управление запасами',
      inventoryId: 'ID запаса',
      goodsId: 'Товар',
      goodsName: 'Название',
      goodsCode: 'Код',
      goodsType: 'Тип',
      batchNo: 'Номер партии',
      quantity: 'Количество',
      availableQuantity: 'Доступно',
      lockedQuantity: 'Заблокировано',
      unit: 'Единица',
      storageLocation: 'Место',
      storageArea: 'Зона',
      storageCabinet: 'Шкаф',
      safetyStock: 'Безопасный запас',
      maxStock: 'Макс. запас',
      expiryDate: 'Срок годности',
      productionDate: 'Дата производства',
      supplier: 'Поставщик',
      status: 'Статус',
      createTime: 'Время создания',
      remark: 'Примечание',
      placeholder: {
        searchInventory: 'Введите название',
        selectLocation: 'Выберите место',
        selectStatus: 'Выберите статус'
      }
    },
    // Stock In Management
    stockIn: {
      title: 'Приход',
      inId: 'ID прихода',
      inNo: 'Номер прихода',
      inType: 'Тип прихода',
      inTime: 'Время прихода',
      goodsName: 'Название',
      goodsCode: 'Код',
      batchNo: 'Номер партии',
      inQuantity: 'Количество',
      unit: 'Единица',
      storageLocation: 'Место',
      supplier: 'Поставщик',
      productionDate: 'Дата производства',
      expiryDate: 'Срок годности',
      operator: 'Оператор',
      operatorName: 'Имя оператора',
      approver: 'Утверждающий',
      approveTime: 'Время утверждения',
      approveStatus: 'Статус утверждения',
      remark: 'Примечание',
      placeholder: {
        searchInNo: 'Введите номер прихода',
        selectType: 'Выберите тип',
        selectStatus: 'Выберите статус'
      }
    },
    // Stock Out Management
    stockOut: {
      title: 'Расход',
      outId: 'ID расхода',
      outNo: 'Номер расхода',
      outType: 'Тип расхода',
      outTime: 'Время расхода',
      goodsName: 'Название',
      goodsCode: 'Код',
      batchNo: 'Номер партии',
      outQuantity: 'Количество',
      unit: 'Единица',
      recipient: 'Получатель',
      recipientDept: 'Отдел',
      recipientPhone: 'Телефон',
      usePurpose: 'Цель использования',
      returnTime: 'Время возврата',
      operator: 'Оператор',
      approver: 'Утверждающий',
      approveTime: 'Время утверждения',
      approveStatus: 'Статус утверждения',
      remark: 'Примечание',
      placeholder: {
        searchOutNo: 'Введите номер расхода',
        selectType: 'Выберите тип',
        selectStatus: 'Выберите статус'
      }
    },
    // Inventory Warning
    warning: {
      title: 'Предупреждение запасов',
      warningId: 'ID предупреждения',
      warningType: 'Тип',
      warningLevel: 'Уровень',
      goodsName: 'Название',
      currentQuantity: 'Текущее количество',
      safetyStock: 'Безопасный запас',
      maxStock: 'Макс. запас',
      warningTime: 'Время предупреждения',
      handler: 'Обработчик',
      handleTime: 'Время обработки',
      handleStatus: 'Статус обработки',
      placeholder: {
        searchGoods: 'Введите название',
        selectType: 'Выберите тип',
        selectLevel: 'Выберите уровень',
        selectStatus: 'Выберите статус'
      }
    }
  },
  // Модуль профиля
  profileModule: {
    title: 'Профиль',
    personalInfo: 'Личная информация',
    myOrder: 'Мои заказы',
    myInspection: 'Мои проверки',
    orderCountMonthly: 'Количество заказов (ежемесячно)',
    inspectionCountMonthly: 'Количество проверок (ежемесячно)',
    userName: 'Имя пользователя',
    nickName: 'Псевдоним',
    sex: 'Пол',
    phone: 'Телефон',
    email: 'Эл. почта',
    dept: 'Отдел',
    post: 'Должность',
    role: 'Роль',
    createTime: 'Время создания',
    editInfo: 'Редактировать',
    resetPwd: 'Сбросить пароль',
    pending: 'В ожидании',
    completed: 'Завершено',
    male: 'Мужской',
    female: 'Женский',
    unknown: 'Неизвестно',
    oldPassword: 'Старый пароль',
    newPassword: 'Новый пароль',
    confirmPassword: 'Подтвердите пароль',
    enterOldPwd: 'Введите старый пароль',
    enterNewPwd: 'Введите новый пароль',
    confirmNewPwd: 'Подтвердите новый пароль',
    pwdNotEmpty: 'Старый пароль не может быть пустым',
    pwdLength: 'Длина от 6 до 20 символов',
    confirmPwdNotEmpty: 'Подтверждение пароля не может быть пустым',
    pwdMismatch: 'Пароли не совпадают',
    modifySuccess: 'Успешно изменено',
    chart: {
      quantity: 'Количество',
      month: 'Месяц',
      finished: 'Завершено',
      unfinished: 'Не завершено'
    },
    placeholder: {
      inputOldPwd: 'Введите старый пароль',
      inputNewPwd: 'Введите новый пароль',
      confirmNewPwd: 'Подтвердите новый пароль'
    }
  },
  // Модуль управления
  controlModule: {
    title: 'Централизованное управление',
    deviceManagement: 'Управление устройствами',
    areaControl: 'Управление зоной',
    remoteOperation: 'Дистанционная операция',
    controlLog: 'Журнал управления',
    deviceControl: 'Управление устройством',
    searchDevice: 'Поиск устройства',
    deviceId: 'ID устройства',
    deviceName: 'Название устройства',
    deviceType: 'Тип устройства',
    location: 'Местоположение',
    status: 'Статус',
    lastOnlineTime: 'Последний раз в сети',
    action: 'Действие',
    control: 'Управление',
    online: 'В сети',
    offline: 'Не в сети',
    waterSourceArea: 'Район источника воды',
    middleStationArea: 'Район средней станции',
    highPoolArea: 'Район высокого бассейна',
    wellArea: 'Район скважины',
    open: 'Открыть',
    close: 'Закрыть',
    areaControlSuffix: 'Управление зоной',
    operationType: 'Тип операции',
    targetDevice: 'Целевое устройство',
    operationParams: 'Параметры операции',
    securityVerify: 'Проверка безопасности',
    inputSecurityCode: 'Введите код безопасности',
    execute: 'Выполнить',
    reset: 'Сброс',
    logId: 'ID журнала',
    operationTypeCol: 'Тип операции',
    target: 'Цель',
    operator: 'Оператор',
    operationTime: 'Время операции',
    result: 'Результат',
    remark: 'Примечание',
    success: 'Успешно',
    fail: 'Неудачно',
    start: 'Старт',
    stop: 'Стоп',
    restart: 'Перезапуск',
    adjust: 'Настроить',
    selectOperationType: 'Выберите тип операции',
    selectDevice: 'Выберите устройство',
    inputParams: 'Введите параметры',
    inputSecurityPwd: 'Введите пароль безопасности',
    deviceNameLabel: 'Название устройства',
    controlCommand: 'Команда управления',
    selectCommand: 'Выберите команду',
    params: 'Параметры',
    inputParamsLabel: 'Введите параметры',
    securityPwd: 'Пароль безопасности',
    cancel: 'Отмена',
    confirm: 'Подтвердить',
    placeholder: {
      searchDevice: 'Поиск устройства',
      selectOperationType: 'Выберите тип операции',
      selectDevice: 'Выберите устройство',
      inputParams: 'Введите параметры операции',
      inputSecurityCode: 'Введите код безопасности',
      selectCommand: 'Выберите команду',
      inputParams: 'Введите параметры',
      inputSecurityPwd: 'Введите пароль безопасности'
    }
  },
  // Модуль измерений
  meteringModule: {
    title: 'Управление измерениями',
    meterManagement: 'Управление счетчиками',
    calibrationPlan: 'План калибровки',
    searchMeter: 'Поиск счетчика',
    meterId: 'ID счетчика',
    meterName: 'Название счетчика',
    type: 'Тип',
    specification: 'Спецификация',
    installationLocation: 'Место установки',
    status: 'Статус',
    lastCalibrationDate: 'Дата последней калибровки',
    nextCalibrationDate: 'Дата следующей калибровки',
    action: 'Действие',
    detail: 'Детали',
    calibrate: 'Калибровать',
    planId: 'ID плана',
    meter: 'Счетчик',
    planCalibrationDate: 'Плановая дата калибровки',
    executor: 'Исполнитель',
    addPlan: 'Добавить план',
    calibrationRecord: 'Запись калибровки',
    startDate: 'Дата начала',
    endDate: 'Дата окончания',
    selectStatus: 'Выберите статус',
    all: 'Все',
    pending: 'В ожидании',
    executing: 'Выполняется',
    completed: 'Завершено',
    cancelled: 'Отменено',
    pass: 'Пройдено',
    fail: 'Не пройдено',
    analysis: 'Анализ данных калибровки',
    qualificationTrend: 'Тренд квалификации',
    deviationDistribution: 'Распределение отклонений',
    meterStatusStatistics: 'Статистика статуса счетчиков',
    export: 'Экспорт записей',
    execute: 'Выполнить',
    viewMeterDetail: 'Просмотр деталей счетчика',
    viewPlanDetail: 'Просмотр плана калибровки',
    viewRecordDetail: 'Просмотр записи калибровки',
    fillCompleteInfo: 'Пожалуйста, заполните полную информацию о плане',
    meterNotExist: 'Выбранный счетчик не существует',
    planAdded: 'План калибровки добавлен',
    startExecute: 'Начать выполнение плана калибровки',
    planCompleted: 'План калибровки завершен',
    exportSuccess: 'Записи калибровки успешно экспортированы',
    placeholder: {
      searchMeter: 'Поиск счетчика',
      selectStatus: 'Выберите статус',
      startDate: 'Дата начала',
      endDate: 'Дата окончания',
      selectMeter: 'Выберите счетчик',
      selectDate: 'Выберите дату',
      inputExecutor: 'Введите исполнителя',
      inputRemark: 'Введите примечание'
    }
  },
  dataBoardModule: {
    title: 'Дашборд данных',
    totalEnergy: 'Общий энергопотребление',
    electricity: 'Электричество',
    water: 'Вода',
    projectOverview: 'Обзор проекта',
    projectTotal: 'Всего проектов',
    meterTotal: 'Всего счетчиков',
    alarmRecord: 'Записи тревог',
    equipmentStatus: 'Статус оборудования',
    alarmInfo: 'Информация о тревоге',
    todayEnergyTrend: 'Тренд энергии сегодня',
    todayEnergyStatistics: 'Статистика энергии сегодня',
    electricityKwh: 'Электричество(кВт·ч)',
    comprehensiveEnergy: 'Комплексная энергия(кгсе)',
    todayWaterUsage: 'Использование воды сегодня(т)',
    dailyPowerCurve: 'Суточная кривая мощности',
    todayWaterInfo: 'Информация о воде сегодня',
    normal: 'Нормальный',
    alarm: 'Тревога',
    offline: 'Офлайн',
    energyPlatform: 'Энергетическая платформа',
    carbonDataVisualizationPlatform: 'Платформа визуализации данных углерода'
  },
  dispatchModule: {
    title: 'Управление диспетчером',
    loadForecast: 'Прогноз нагрузки',
    priceForecast: 'Прогноз цены',
    weatherForecast: 'Прогноз погоды',
    modelManagement: 'Управление моделями',
    costSaving: 'Экономия затрат',
    efficiencyImprovement: 'Повышение эффективности',
    emissionReduction: 'Снижение выбросов углерода',
    trendAnalysis: 'Анализ трендов'
  },
  toolModule: {
    title: 'Системные инструменты',
    basicInfo: 'Основная информация',
    columnInfo: 'Информация о столбце',
    genInfo: 'Информация о генерации',
    fieldProperties: 'Свойства поля',
    formProperties: 'Свойства формы'
  },
  equipmentModule: {
    title: 'Управление оборудованием',
    equipmentInfo: 'Информация об оборудовании',
    equipmentAlarm: 'Тревога оборудования',
    equipmentData: 'Данные оборудования',
    equipmentDocuments: 'Документы оборудования'
  },
  dataQueryModule: {
    title: 'Запрос данных',
    dailyRawData: 'Ежедневные сырые данные',
    dailyPeakData: 'Ежедневные пиковые данные'
  },
  componentsModule: {
    title: 'Компоненты',
    icons: 'Иконки',
    elementIcons: 'Иконки Element-UI'
  },
  autoeeModule: {
    title: 'Автоматизация',
    stockInRecord: 'Запись прихода',
    stockOutRecord: 'Запись расхода'
  },
  alarmModule: {
    title: 'Управление тревогами',
    alarmAnalysis: 'Анализ тревог',
    alarmHistory: 'История тревог',
    alarmRule: 'Правило тревоги',
    realtimeAlarm: 'Тревога в реальном времени',
    paramName: 'Имя параметра',
    alarmTime: 'Время тревоги',
    alarmInfo: 'Информация о тревоге',
    alarmLevel: 'Уровень тревоги',
    alarmArea: 'Область тревоги',
    alarmEquipment: 'Оборудование тревоги',
    alarmVal: 'Значение тревоги',
    endTime: 'Время окончания',
    alarmParam: 'Параметр тревоги',
    alarmCount: 'Количество тревог',
    alarmType: 'Тип тревоги',
    eventType: 'Тип события',
    condition1: 'Условие 1',
    thresholdValue1: 'Порог 1',
    condition2: 'Условие 2',
    thresholdValue2: 'Порог 2',
    userId: 'Напоминание',
    createOrderSwitch: 'Автоматическое создание заказа',
    alarmSwitch: 'Переключатель тревоги',
    alarmDesc: 'Описание тревоги',
    addAlarm: 'Добавить тревогу в реальном времени',
    editAlarm: 'Редактировать тревогу в реальном времени',
    addAlarmRule: 'Добавить правило тревоги',
    editAlarmRule: 'Редактировать правило тревоги'
  },
  analysisReportModule: {
    title: 'Аналитический отчет',
    generateReport: 'Сгенерировать аналитический отчет',
    print: 'Печать',
    reportName: 'Аналитический отчет',
    energyStatistics: '1. Статистика энергопотребления',
    electricityUsage: '2. Потребление электроэнергии',
    electricityFee: '3. Электросчет',
    waterUsage: '4. Потребление воды',
    waterFee: '5. Водный счет',
    recurringRate: '6. Повторяющаяся ставка',
    electricityTotal: 'За этот период общее потребление электроэнергии составило {value}кВт·ч, максимальное потребление электроэнергии {max}кВт·ч, максимальная нагрузка произошла в {date}.',
    electricityFeeTotal: 'За этот период общий электросчет составляет ￥{value}',
    waterTotal: 'За этот период общее потребление воды составило {value}т, максимальное потребление воды {max}т, максимальный расход произошел в {date}.',
    waterFeeTotal: 'За этот период общий водный счет составляет ￥{value}',
    suggestion: 'Предложение: Увеличить количество контрольных цепей в нижней части трансформатора для сбора конечных данных, определить энергопотребление каждого контрольного пункта и проверить наличие потерь электроэнергии. Или заменить освещение и другое оборудование в здании энергосберегающими продуктами для снижения энергопотребления и достижения энергосбережения и снижения выбросов.'
  },
  cameraModule: {
    title: 'Управление камерами',
    realtimeVideo: 'Видео в реальном времени',
    cameraConfig: 'Конфигурация камеры',
    cameraName: 'Имя камеры',
    cameraBrand: 'Бренд',
    cameraSn: 'Серийный номер',
    cameraIp: 'IP камеры',
    cameraPort: 'Порт',
    cameraUser: 'Имя пользователя',
    cameraPassword: 'Пароль',
    cameraType: 'Тип камеры',
    cameraStatus: 'Статус',
    cameraLocation: 'Место установки',
    addCamera: 'Добавить камеру',
    editCamera: 'Редактировать камеру'
  },
  carbonAssetsModule: {
    title: 'Управление углеродными активами',
    carbonAnalysis: 'Анализ выбросов углерода',
    monthlyCarbon: 'Ежемесячные выбросы углерода',
    yearlyCarbon: 'Годовые выбросы углерода',
    currentMonth: 'Текущий месяц',
    lastMonth: 'Прошлый месяц',
    currentYear: 'Текущий год',
    lastYear: 'Прошлый год',
    trend: 'Тенденция',
    energyType: 'Тип энергии',
    year: 'Год'
  },
  energyAnalysisModule: {
    title: 'Анализ энергопотребления',
    energyOverview: 'Обзор энергопотребления',
    energyFlow: 'Энергетический поток',
    energyTrend: 'Тенденция потребления',
    yoyAnalysis: 'Анализ YoY',
    monAnalysis: 'Анализ MoM',
    lossAnalysis: 'Анализ потерь',
    expenseBoard: 'Панель затрат',
    expenseReport: 'Отчет затрат',
    monthOnMonth: 'MoM',
    todayEnergy: 'Сегодня',
    yesterdayEnergy: 'Вчера',
    currentMonthEnergy: 'Текущий месяц',
    lastMonthEnergy: 'Прошлый месяц',
    currentYearEnergy: 'Текущий год',
    lastYearEnergy: 'Прошлый год',
    trend: 'Тенденция',
    area: 'Область',
    energyType: 'Тип энергии'
  },
  itemizedAnalysisModule: {
    title: 'Позиционный анализ',
    itemizedOverview: 'Позиционный обзор',
    energyType: 'Тип энергии',
    date: 'Дата',
    search: 'Поиск'
  },
  maintenanceModule: {
    title: 'Управление обслуживанием',
    duty: 'Управление дежурством',
    inspectionPlan: 'План инспекции',
    inspectionRecord: 'Записи инспекции',
    repairOrder: 'Заказ на ремонт',
    schedule: 'Управление расписанием'
  },
  quotaModule: {
    title: 'Управление квотами',
    analysis: 'Анализ квот',
    config: 'Конфигурация квот',
    monitor: 'Мониторинг квот'
  },
  systemModule: {
    title: 'Управление системой',
    config: 'Конфигурация параметров',
    dept: 'Управление отделами',
    dict: 'Управление словарями',
    menu: 'Управление меню',
    notice: 'Управление уведомлениями',
    post: 'Управление должностями',
    role: 'Управление ролями',
    user: 'Управление пользователями'
  },
  digitaltwinModule: {
    title: 'Цифровой двойник'
  },
  inventoryModule: {
    title: 'Управление инвентарем',
    attachment: 'Управление вложениями',
    purveyor: 'Управление поставщиками'
  },
  managementSystemModule: {
    title: 'Система управления',
    prePlan: 'Управление планами',
    process: 'Управление процессами',
    regulation: 'Управление правилами',
    standard: 'Управление стандартами'
  },
  monitorModule: {
    title: 'Мониторинг системы',
    admin: 'Системный администратор',
    cache: 'Мониторинг кэша',
    logininfor: 'Журнал входа',
    online: 'Онлайн пользователи',
    operlog: 'Журнал операций',
    xxljob: 'Планируемые задачи'
  },
  newenergyModule: {
    title: 'Новая энергия',
    energyStorage: 'Хранилище энергии',
    microGrid: 'Микросеть',
    pvStation: 'ФЭС станция',
    storageBattery: 'Аккумуляторная батарея'
  },
  reportModule: {
    title: 'Управление отчетами',
    generate: 'Генерация отчета',
    template: 'Шаблон отчета'
  }
}
