package com.app.listaprzebojow;

import com.app.listaprzebojow.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ListaPrzebojowApplication  {

    private final SongRepository songRepository;

    public static void main(String[] args) {
        SpringApplication.run(ListaPrzebojowApplication.class, args);
    }

}

/*
Lista przebojow

Mamy w programie dwie role USER oraz ADMIN.

ADMIN dodaje nowe utwory do bazy danych. Kazdy utwor ma tytul, wykonawce, gatunek ( enum ROCK, POP, DISCO ).
USER moze na utwor oddac glos. W zwiazku z czym masz yabele z uzytkownikami i glosowac moga tylko ci ktorzy maja role USER.
ADMIN nie moze zaglosowac. Kazdy glos musi zostac opatrzony data kiedy zaglosowano.

Na tej podstawie musisz przygotowac nastepujace funkcjonalnosci:
a. zestawienie najlepszych utworow wogole
b. zestawienie najlepszych utworow w danym dniu - jezeliw danym dniu nikt nie glosowal to wyjatek
c. zestawienie najlepszych utworow wzgledem wybranego gatunku muzycznego

Ustalamy ze w liscie przebojow masz max 10 pozycji.

Dodatkowo mozesz przewidziec przechowywanie linka do utworu na YT.
Ewentualnie mozesz spojrzec w API Youtube i pobierac spod linku utwor np zapisywac go do AWS i wtedy oprocz linka na YT masz tez link na AWS.

Kazdy uzytkownik moze rowniez stworzyc sobie swoja playliste.
Mamy dwie mozliwosci:
a. lista tworzona recznie czyli user najpierw tworzy pusta playliste a potem dodaje do niej utwory
b. program generuje automatycznie liste na podstawie preferencji usera. User podaje gatunek, ulubionego wykonawce i co jeszcze wymyslisz.
   oczywiscie podaje tez ile chce miec utworow w liscie i na tej podstawie program sugerujac sie popularnoscia utworow generuje liste.
 */
