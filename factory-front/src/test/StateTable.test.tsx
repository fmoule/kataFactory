import {ActionState, convertIntoActionMap} from "../components/StateTable";


describe("General tests", () => {

    it("should convert actions into map", () => {
        let actions: Array<ActionState> = [];
        actions.push({name: "action1", nbRobots: 4});
        actions.push({name: "action2", nbRobots: 2});
        actions.push({name: "action3", nbRobots: 1});
        let map: Map<string, number> = convertIntoActionMap(actions);
        expect(map.size).toBe(3);

        // Test the datas :
        expect(map.has("action1")).toBe(true);
        expect(map.get("action1")).toBe(4);
        expect(map.get("action2")).toBe(2);
        expect(map.get("action3")).toBe(1);
        expect(map.has("action4")).toBe(false);
    });
})

export {};