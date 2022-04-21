<template>
  <div class="imui-center">
    <lemon-imui width="1203px"
                height="500px"
                :user="user"
                ref="IMUI"
                :theme="theme"
                @change-menu="handleChangeMenu"
                @change-contact="handleChangeContact"
                @pull-messages="handlePullMessages"
                @menu-avatar-click="handleMenuAvatarClick"
                @send="handleSend">
      <template #cover>
        <div class="cover">
          <img src="../assets/demo-icon.png"
               alt=""
               class="icon">
        </div>
      </template>
      <template #message-title="contact">
        <span>{{ contact.displayName }}</span>
        <br />
      </template>
    </lemon-imui>
  </div>
</template>

<script>
import EmojiData from "../assets/emoji";

const getTime = () => {
  return new Date().getTime();
};
const generateRandId = () => {
  return Math.random()
    .toString(36)
    .substr(-8);
};
const generateRandWord = () => {
  return Math.random()
    .toString(36)
    .substr(2);
};
const generateMessage = (toContactId = "", fromUser) => {
  if (!fromUser) {
    fromUser = {
      id: "system",
      displayName: "系统测试",
      avatar: "http://upload.qqbodys.com/allimg/1710/1035512943-0.jpg",
    };
  }
  return {
    id: generateRandId(),
    status: "succeed",
    type: "text",
    sendTime: getTime(),
    content: generateRandWord(),
    //fileSize: 1231,
    //fileName: "asdasd.doc",
    toContactId,
    fromUser,
  };
};

export default {
  name: "app",
  data () {
    return {
      theme: "default",
      user: {
        id: "1",
        displayName: "June",
        avatar: require('../assets/5.jpg'),
      },
    };
  },
  mounted () {
    const contactData1 = {
      id: "contact-1",
      displayName: "工作协作群",
      avatar: require('../assets/5.jpg'),
      index: "[1]群组",
      unread: 0,
      lastSendTime: 1566047865417,
      lastContent: "2",
    };
    const contactData2 = {
      id: "contact-2",
      displayName: "铁牛",
      avatar: require('../assets/5.jpg'),
      index: "T",
      unread: 32,
      lastSendTime: 3,
      lastContent: "你好123",
    };
    const contactData3 = {
      id: "contact-3",
      displayName: "wgg",
      avatar: require('../assets/5.jpg'),
      index: "Z",
      unread: 10,
      lastSendTime: 3,
      lastContent: "你好123",
    };
    const { IMUI } = this.$refs;
    let contactList = [
      { ...contactData1 },
      { ...contactData2 },
      { ...contactData3 },
    ];

    IMUI.initContacts(contactList);
    IMUI.initEmoji(EmojiData);
  },
  methods: {
    handleMenuAvatarClick () {
      console.log("Event:menu-avatar-click");
    },
    handleChangeContact (contact, instance) {
      console.log("Event:change-contact");
      instance.updateContact({
        id: contact.id,
        unread: 0,
      });
    },
    handleSend (message, next, file) {
      console.log(message, next, file);
      setTimeout(() => {
        next();
      }, 1000);
    },
    handlePullMessages (contact, next, instance) {
      const otheruser = {
        id: "contact-2",
        displayName: "铁牛",
        avatar: require('../assets/5.jpg'),
      };
      const _otheruser = {
        id: "contact-3",
        displayName: "wgg",
        avatar: require('../assets/5.jpg'),
      };
      setTimeout(() => {
        const messages = [
          generateMessage(instance.currentContactId, this.user),
          generateMessage(instance.currentContactId, otheruser),
          generateMessage(instance.currentContactId, this.user),
          generateMessage(instance.currentContactId, otheruser),
          generateMessage(instance.currentContactId, _otheruser),
        ];
        next(messages, true);
      }, 500);
    },
    handleChangeMenu () {
      console.log("Event:change-menu");
    },
  },
};
</script>

<style scoped>
.imui-center {
  margin: 0 30px;
  margin-top: 60px;
}
.cover {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.icon {
  height: 150px;
  width: inherit;
}

input#switch[type='checkbox'] {
  height: 0;
  width: 0;
  display: none;
}

label#switch-label {
  cursor: pointer;
  text-indent: -9999px;
  width: 34px;
  height: 20px;
  background: #aaa;
  display: block;
  border-radius: 100px;
  position: relative;
}

label#switch-label:after {
  content: '';
  position: absolute;
  top: 2px;
  left: 2px;
  width: 16px;
  height: 16px;
  background: #fff;
  border-radius: 20px;
  transition: 0.3s;
}

input#switch:checked + label {
  background: #0fd547;
}

input#switch:checked + label:after {
  left: calc(100% - 2px);
  transform: translateX(-100%);
}

label#switch-label:active:after {
  width: 20px;
}
</style>