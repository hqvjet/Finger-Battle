package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player2 {
    int hp, atk, stamina, status;

    public Player2(int hp, int atk, int stamina) {
        this.hp = hp;
        this.atk = atk;
        this.stamina = stamina;
    }
}
