package net.mega.tutorialmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.mega.tutorialmod.block.ModBlocks;
import net.mega.tutorialmod.item.ModItems;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> RUBY_SMELTABLES = List.of(ModItems.RAW_RUBY, ModBlocks.DEEPSLATE_RUBY_ORE,
            ModBlocks.NETHER_RUBY_ORE, ModBlocks.END_STONE_RUBY_ORE);

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    //this may be using exporter instead of consumer due to a difference in the fabric API between the version the
    //tutorial is at from the version I am at, further research required
    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY, 0.7f, 200, "ruby");
        offerBlasting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY, 0.7f, 200, "ruby");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY, RecipeCategory.DECORATIONS, ModBlocks.RUBY_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_RUBY, 1)
                .pattern("SSS")
                .pattern("SRS")
                .pattern("SSS")
                .input('S', Items.STONE)
                .input('R', ModItems.RUBY)
                .criterion(hasItem(Items.STONE), conditionsFromItem(Items.STONE))
                .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.RAW_RUBY)));

        createStairsRecipe(ModBlocks.RUBY_STAIRS, Ingredient.ofItems(ModBlocks.RUBY_BLOCK))
                .criterion(hasItem(ModBlocks.RUBY_BLOCK), conditionsFromItem(ModBlocks.RUBY_BLOCK))
                .offerTo(exporter);
        //DISABLED BECAUSE CHONKY, THERE IS A SMALLER METHOD
        //createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBY_SLAB, Ingredient.ofItems(ModBlocks.RUBY_BLOCK))
        //        .criterion(hasItem(ModBlocks.RUBY_BLOCK), conditionsFromItem(ModBlocks.RUBY_BLOCK))
        //        .offerTo(exporter);
        offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBY_SLAB, ModBlocks.RUBY_BLOCK);
        //button recipe here, NOT INCLUDED DUE TO RECIPE CONFLICT
        offerPressurePlateRecipe(exporter, ModBlocks.RUBY_PRESSURE_PLATE, ModBlocks.RUBY_BLOCK);
        //insert fence here
        //insert gate here
        //WALL DISABLED DUE TO CONFLICTING RECIPE
        //offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBY_WALL, ModBlocks.RUBY_BLOCK);
        createDoorRecipe(ModBlocks.RUBY_DOOR, Ingredient.ofItems(ModBlocks.RUBY_BLOCK))
                .criterion(hasItem(ModBlocks.RUBY_BLOCK), conditionsFromItem(ModBlocks.RUBY_BLOCK))
                .offerTo(exporter);
        createTrapdoorRecipe(ModBlocks.RUBY_TRAPDOOR, Ingredient.ofItems(ModBlocks.RUBY_BLOCK))
                .criterion(hasItem(ModBlocks.RUBY_BLOCK), conditionsFromItem(ModBlocks.RUBY_BLOCK))
                .offerTo(exporter);
    }
}
