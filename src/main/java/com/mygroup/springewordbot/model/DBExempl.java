package com.mygroup.springewordbot.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class DBExempl {

 private List<User> users;
 private List<Word> words;


 public DBExempl() {
  this.users = new ArrayList<>();
  this.words = new ArrayList<>();
  init();
 }

 public void init(){

  LocalDate licenseEnd=LocalDate.now().plusDays(10);
  users.add(new User(1,licenseEnd));
  users.add(new User(137588343L,licenseEnd));

  words.add(new Word("Adventure","ədˈvɛn.tʃər","Пригода",
          "Going on a solo hiking trip was a thrilling adventure for Sarah.",
          "Подорож у гори в одиночестві була захопливою пригодою для Сари."));
  words.add(new Word("Efficient","ɪˈfɪʃ.ənt","Ефективний",
          "Using a programmable thermostat is an efficient way to manage energy consumption at home.",
          "Використання програмованого термостата - це ефективний спосіб керування споживанням енергії вдома."));
  words.add(new Word("Illuminate","ɪˈluː.mɪ.neɪt","Підсвічувати",
          "The streetlights helped illuminate the path during the dark night.",
          "Ліхтарі допомогли підсвітити стежку під час темної ночі."));
  words.add(new Word("Resilient","rɪˈzɪl.jənt","Стійкий, витривалий",
          "Despite facing many challenges, the community showed a resilient spirit and quickly recovered.",
          "Незважаючи на багато труднощів, спільнота проявила стійкий дух і швидко відновилася."));
  words.add(new Word("Versatile","ˈvɜː.sə.taɪl","Універсальний",
          "A Swiss Army knife is a versatile tool that can be used for various purposes, such as cutting and opening bottles.",
          "Швейцарський армійський ніж - це універсальний інструмент, який можна використовувати для різних цілей, таких як різання і відкривання пляшок."));



 }

 }
