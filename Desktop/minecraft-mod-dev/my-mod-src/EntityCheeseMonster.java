import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class EntityCheeseMonster extends EntityMob
{
    private int angerLevel = 0;
    private int randomSoundDelay = 0;
    public EntityCheeseMonster(World par1World)
    {
        super(par1World);
        this.texture = "test.png";
        this.moveSpeed = 0.25F;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveForward, false));
        this.tasks.addTask(4, new EntityAIWander(this, this.moveSpeed));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true ));



    }
    public int getTotalArmorValue()
    {
         return 10;
    }public void onUpdate()
{
    this.moveSpeed = this.entityToAttack != null ? 0.95F : 0.5F;

    if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0)
    {
        this.playSound("mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
    }

    super.onUpdate();
}

    @SideOnly(Side.CLIENT)


    public boolean getCanSpawnHere()
    {
        return this.worldObj.difficultySetting > 0 && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Anger", (short)this.angerLevel);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.angerLevel = par1NBTTagCompound.getShort("Anger");
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        return this.angerLevel == 0 ? null : super.findPlayerToAttack();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (this.isEntityInvulnerable())
        {
            return true;
        }
        else
        {
            Entity entity = par1DamageSource.getEntity();

            if (entity instanceof EntityPlayer)
            {
                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0D, 32.0D, 32.0D));

                for (int j = 0; j < list.size(); ++j)
                {
                    Entity entity1 = (Entity)list.get(j);

                    if (entity1 instanceof EntityCheeseMonster)
                    {
                        EntityCheeseMonster entityCheeseMonstere = (EntityCheeseMonster)entity1;
                        entityCheeseMonstere.becomeAngryAt(entity);
                    }
                }

                this.becomeAngryAt(entity);
            }

            return super.attackEntityFrom(par1DamageSource, par2);
        }
    }

    /**
     * Causes this PigZombie to become angry at the supplied Entity (which will be a player).
     */
    private void becomeAngryAt(Entity par1Entity)
    {
        this.entityToAttack = par1Entity;
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
    }




    protected boolean isAIEnabled()
    {
        return false;
    }

    public int getMaxHealth()
    {
        return 10;
    }

    public int getAttackStrength(Entity par1Entity)
    {
        return 1;
    }
    public EnumCreatureAttribute getCreatureAttribute()
    {
         return EnumCreatureAttribute.UNDEAD;
    }

    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        return false;
    }

    protected void dropRareDrop(int par1)
    {
        this.dropItem(Item.diamond.itemID, 1);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.record13.itemID;
    }

    /**
     * Makes entity wear random armor based on difficulty
     */
    protected void addRandomArmor()
    {
        this.setCurrentItemOrArmor(0, new ItemStack(Item.record13));
    }


    {




    }
}




