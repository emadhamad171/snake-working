package com.codenjoy.dojo.snake.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.*;
import com.codenjoy.dojo.snake.model.Elements;

import java.util.List;
import java.util.Optional;

///**
// * User: your name
// */
//public class YourSolver implements Solver<Board> {
//
//    private Dice dice;
//    private Board board;
//
//
//    public YourSolver(Dice dice) {
//        this.dice = dice;
//    }
//    @Override
//    public String get(Board board) {
//        this.board = board;
//        System.out.println(board.toString());
//       List apples = board.getApples();
//
//
//
//        return (
//         Direction.STOP.toString()
//
//
//        );
//    }

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

//import com.codenjoy.dojo.client.Solver;
//import com.codenjoy.dojo.client.WebSocketRunner;
//import com.codenjoy.dojo.services.Dice;
//import com.codenjoy.dojo.services.Direction;
//import com.codenjoy.dojo.services.Point;
//import com.codenjoy.dojo.services.RandomDice;
//import client.BoardLee;
//
//import java.util.List;
//import java.util.Optional;

/**
 * User: your name
 */
public class YourSolver implements Solver<Board> {

    public List<Point> getWallsSnake() {
        List<Point> result = board.getSnake();

        result.addAll(board.getWalls());
        return result;
    }

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    public int invertVervical(int val, int dimY) {
        return dimY - val - 1;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        if (board.isGameOver()) return "";
        char[][] field = board.getField();
        int sizeX = field.length;
        int sizeY = field[0].length;
        BoardLee boardLee = new BoardLee(sizeX, sizeY);
        List<Point> barriers = board.getBarriers();
        barriers.forEach(p -> boardLee.setObstacle(p.getX(), invertVervical(p.getY(), sizeY)));

        BoardLee boardLee1 = new BoardLee(sizeX, sizeY);
        List<Point> snakeWalls = getWallsSnake();
        List<Point> snake = getWallsSnake();


        snakeWalls.forEach(p2 -> boardLee1.setObstacle(p2.getX(), invertVervical(p2.getY(), sizeY)));

        BoardLee boardLee2 = new BoardLee(sizeX, sizeY);
        snake.forEach(p3 -> boardLee2.setObstacle(p3.getX(), invertVervical(p3.getY(), sizeY)));

        Point me = board.getHead();
        List<Point> length = board.getSnake();

        Point redApple = board.getStones().get(0);
        int arrayLength = length.size();


        Point apples = board.getApples().get(0);

        PointLee src = new PointLee(me.getX(), invertVervical(me.getY(), sizeY));

        PointLee dst = new PointLee(apples.getX(), invertVervical(apples.getY(), sizeY));
        PointLee dst2 = new PointLee(redApple.getX(), invertVervical(redApple.getY(), sizeY));
        PointLee src3 = new PointLee(apples.getX(), invertVervical(apples.getY(), sizeY));
        PointLee dst3 = new PointLee(redApple.getX(), invertVervical(redApple.getY(), sizeY));

        Optional<List<PointLee>> solution = boardLee.trace(src,  dst);
        Optional<List<PointLee>> solution2 = boardLee1.trace(src, dst2);
//        Optional<List<PointLee>> solution3 = boardLee.trace(src3, dst3);


        System.out.print(dst3);
        System.out.print(src3);
//        boardLee.printMe();


        if (arrayLength < 40 && solution.isPresent()) {

            List<PointLee> path = solution.get();
            PointLee p = path.stream().skip(1).findFirst().get();

            int to_x = p.x();
            int to_y = invertVervical(p.y(), sizeY);
            System.out.printf("TO: x:%2d, y:%2d\n", to_x, to_y);
            if (to_y < me.getY()) return Direction.DOWN.toString();
            if (to_y > me.getY()) return Direction.UP.toString();
            if (to_x < me.getX()) return Direction.LEFT.toString();
            if (to_x > me.getX()) return Direction.RIGHT.toString();

        }


        if (solution2.isPresent()) {
            List<PointLee> path2 = solution2.get();
            PointLee p2 = path2.stream().skip(1).findFirst().get();


            int to_x2 = p2.x();
            int to_y2 = invertVervical(p2.y(), sizeY);
            if (to_y2 < me.getY()) return Direction.DOWN.toString();
            if (to_y2 > me.getY()) return Direction.UP.toString();
            if (to_x2 < me.getX()) return Direction.LEFT.toString();
            if (to_x2 > me.getX()) return Direction.RIGHT.toString();


        }


//            follow your tail snake ;
//            она просчитывает путь на перед не будет ли мишать хвост, а не на данный момент

        return Direction.ACT.toString();

    }


    public static void main(String[] args) {
        WebSocketRunner.runClient(
                "http://206.81.16.237/codenjoy-contest/board/player/75tkyabo6z38970m911b?code=6299784486961358095",

                new YourSolver(new RandomDice()),
                new Board());
    }

}
