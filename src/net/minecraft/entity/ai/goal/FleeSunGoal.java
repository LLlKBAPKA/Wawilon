package net.minecraft.entity.ai.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class FleeSunGoal extends Goal
{
    protected final CreatureEntity creature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private final double movementSpeed;
    private final World world;

    public FleeSunGoal(CreatureEntity theCreatureIn, double movementSpeedIn)
    {
        this.creature = theCreatureIn;
        this.movementSpeed = movementSpeedIn;
        this.world = theCreatureIn.world;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute()
    {
        if (this.creature.getAttackTarget() != null)
        {
            return false;
        }
        else if (!this.world.isDaytime())
        {
            return false;
        }
        else if (!this.creature.isBurning())
        {
            return false;
        }
        else if (!this.world.canSeeSky(this.creature.getPosition()))
        {
            return false;
        }
        else
        {
            return !this.creature.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty() ? false : this.isPossibleShelter();
        }
    }

    protected boolean isPossibleShelter()
    {
        Vector3d vector3d = this.findPossibleShelter();

        if (vector3d == null)
        {
            return false;
        }
        else
        {
            this.shelterX = vector3d.x;
            this.shelterY = vector3d.y;
            this.shelterZ = vector3d.z;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return !this.creature.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.creature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    @Nullable
    protected Vector3d findPossibleShelter()
    {
        Random random = this.creature.getRNG();
        BlockPos blockpos = this.creature.getPosition();

        for (int i = 0; i < 10; ++i)
        {
            BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

            if (!this.world.canSeeSky(blockpos1) && this.creature.getBlockPathWeight(blockpos1) < 0.0F)
            {
                return Vector3d.copyCenteredHorizontally(blockpos1);
            }
        }

        return null;
    }
}
