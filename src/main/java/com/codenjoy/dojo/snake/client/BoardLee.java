package com.codenjoy.dojo.snake.client;

import com.codenjoy.dojo.services.Point;

import java.util.*;
import java.util.stream.*;

public class BoardLee {
    private final List<PointLee> deltas = new ArrayList<PointLee>(){{
//        add(new Point(-1,-1));
        add(new PointLee(0,-1));
//        add(new Point(+1,-1));
        add(new PointLee(-1,0));
        add(new PointLee(+1,0));
//        add(new Point(-1,+1));
        add(new PointLee(0,+1));
//        add(new Point(+1,+1));
    }};
    private final static int OBSTACLE = -10;
    private final static int START = -1;
    private final int dimX;
    private final int dimY;
    private int[][] data;

    public BoardLee(int dimX, int dimY) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.data = new int[dimY][dimX];
    }

    public String element(PointLee p, boolean isFinal, List<PointLee> path) {
        int val = get(p);
        if (val == OBSTACLE) {
            return " XX ";
        }

        if (!isFinal) {
            return String.format("%3d ", val);
        } else if (path.contains(p)){
            return String.format("%3d ", val);
        } else {
            return "    ";
        }
    }

    void printMe() {
        printMe(false, new ArrayList<>());
    }

    void printMe(boolean isFinal, List<PointLee> path) {
        for (int row = 0; row < dimY; row++) {
            for (int col = 0; col < dimX; col++) {
                PointLee p = new PointLee(col, row);
                System.out.printf(element(p, isFinal, path));
            }
            System.out.println();
        }
        System.out.println();
    }

    int get(PointLee p) {
        return this.data[p.y()][p.x()];
    }

    void set(PointLee p, int val) {
        this.data[p.y()][p.x()] = val;
    }

    boolean isOnBoard(PointLee p) {
        return p.x()>= 0 && p.x() < dimX && p.y()>=0 && p.y()< dimY;
    }

    boolean isUnvisited(PointLee p) {
        return get(p) == 0;
    }

    Set<PointLee> neighbors(PointLee point) {
        return deltas.stream()
                .map(d -> d.move(point))
                .filter(p -> isOnBoard(p))
                .collect(Collectors.toSet());
    }

    Set<PointLee> neighborsUnvisited(PointLee point) {
        return neighbors(point).stream()
                .filter(p -> isUnvisited(p))
                .collect(Collectors.toSet());
    }

    PointLee neighborByValue(PointLee point, int value) {
        return neighbors(point).stream()
                .filter(p -> get(p) == value)
                .findFirst()
                .get();
    }




    public void setObstacle(int x, int y) {
        setObstacle(new PointLee(x, y));
    }

    void setObstacle(PointLee p) {
        set(p, OBSTACLE);
    }

    public Optional<List<PointLee>> trace(PointLee start, PointLee finish) {
        boolean found = false;
        set(start, START);
        Set<PointLee> curr = new HashSet<>();
        int counter[] = new int[]{0};
        curr.add(start);
        while (!curr.isEmpty() && !found) {
            System.out.println();
            counter[0]++;
            Set<PointLee> next = curr.stream().map(p -> neighborsUnvisited(p)).flatMap(Collection::stream).collect(Collectors.toSet());
            next.forEach(p -> set(p, counter[0]));
            if (next.contains(finish)) {
                found = true;
            }
//            printMe();
            curr.clear();
            curr.addAll(next);
            next.clear();
        }

        if (found) {
            set(start, 0);
            ArrayList<PointLee> path = new ArrayList<>();
            path.add(finish);
            PointLee curr_p = finish;
            while (counter[0]>0) {
                counter[0]--;
                PointLee prev_p = neighborByValue(curr_p, counter[0]);
                path.add(prev_p);
                curr_p = prev_p;
            }
            Collections.reverse(path);
            return Optional.of(path);
        } else {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        BoardLee b = new BoardLee(15, 15);
        PointLee p1 = new PointLee(0, 0);
        PointLee p2 = new PointLee(14, 14);
        b.setObstacle(new PointLee(0,0));
        b.setObstacle(new PointLee(1,0));
        b.setObstacle(new PointLee(2,0));
        b.setObstacle(new PointLee(3,0));
        b.setObstacle(new PointLee(4,0));
        b.setObstacle(new PointLee(5,0));
        b.setObstacle(new PointLee(6,0));
        b.setObstacle(new PointLee(7,0));
        b.setObstacle(new PointLee(8,0));
        b.setObstacle(new PointLee(9,0));
        b.setObstacle(new PointLee(10,0));
        b.setObstacle(new PointLee(11,0));
        b.setObstacle(new PointLee(12,0));
        b.setObstacle(new PointLee(13,0));
        b.setObstacle(new PointLee(14,0));

        b.setObstacle(new PointLee(1,14));
        b.setObstacle(new PointLee(2,14));
        b.setObstacle(new PointLee(3,14));
        b.setObstacle(new PointLee(4,14));
        b.setObstacle(new PointLee(5,14));
        b.setObstacle(new PointLee(6,14));
        b.setObstacle(new PointLee(7,14));
        b.setObstacle(new PointLee(8,14));
        b.setObstacle(new PointLee(9,14));
        b.setObstacle(new PointLee(10,14));
        b.setObstacle(new PointLee(11,14));
        b.setObstacle(new PointLee(12,14));
        b.setObstacle(new PointLee(13,14));
        b.setObstacle(new PointLee(14,14));

        b.setObstacle(new PointLee(14,1));
        b.setObstacle(new PointLee(14,2));
        b.setObstacle(new PointLee(14,3));
        b.setObstacle(new PointLee(14,4));
        b.setObstacle(new PointLee(14,5));
        b.setObstacle(new PointLee(14,6));
        b.setObstacle(new PointLee(14,7));
        b.setObstacle(new PointLee(14,8));
        b.setObstacle(new PointLee(14,9));
        b.setObstacle(new PointLee(14,10));
        b.setObstacle(new PointLee(14,11));
        b.setObstacle(new PointLee(14,12));
        b.setObstacle(new PointLee(14,13));
        b.setObstacle(new PointLee(14,14));
        b.setObstacle(new PointLee(14,14));

        b.setObstacle(new PointLee(0,1));
        b.setObstacle(new PointLee(0,2));
        b.setObstacle(new PointLee(0,3));
        b.setObstacle(new PointLee(0,4));
        b.setObstacle(new PointLee(0,5));
        b.setObstacle(new PointLee(0,6));
        b.setObstacle(new PointLee(0,7));
        b.setObstacle(new PointLee(0,8));
        b.setObstacle(new PointLee(0,9));
        b.setObstacle(new PointLee(0,10));
        b.setObstacle(new PointLee(0,11));
        b.setObstacle(new PointLee(0,12));
        b.setObstacle(new PointLee(0,13));
        b.setObstacle(new PointLee(0,14));
        b.setObstacle(new PointLee(0,14));

        b.printMe();
        Optional<List<PointLee>> result = b.trace(p1, p2);
        if (result.isPresent()) {
            System.out.println("Path has been found");
            List<PointLee> path = result.get();
            path.forEach(System.out::println);
            b.printMe(true, path);
        } else  {
            System.out.println("Trace can't be found");
        }
    }

}
